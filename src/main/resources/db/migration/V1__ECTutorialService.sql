--
-- COMMON FUNCTIONS
--
CREATE OR REPLACE FUNCTION update_modified_on_column()
    RETURNS TRIGGER AS $$
BEGIN
  NEW.modified_on = now();
  RETURN NEW;
END;
$$ language 'plpgsql';

--
-- TABLES
--

-- Represents a user in the system.
CREATE TABLE IF NOT EXISTS platform_user (
    created_on TIMESTAMP WITH TIME ZONE DEFAULT NOW(),-- When the user account was created.
    email TEXT NOT NULL,-- The user's Email address that is also their username.
    enabled BOOLEAN NOT NULL,-- If set the user is allowed to login, otherwise they cannot log in.
    encoded_password TEXT NOT NULL,-- The user's password encoded so not in plain text.
    first_name TEXT NOT NULL,-- The user's first (given) name.
    last_name TEXT NOT NULL,-- The user's last (family) name.
    modified_on TIMESTAMP WITH TIME ZONE,-- When the user account was last modified.
    user_id UUID PRIMARY KEY NOT NULL
);

CREATE TRIGGER platform_user_updatemodifiedon
BEFORE INSERT OR UPDATE ON platform_user
FOR EACH ROW EXECUTE PROCEDURE update_modified_on_column();

COMMENT ON TABLE platform_user IS 'Represents a user in the system.';
COMMENT ON COLUMN platform_user.first_name IS 'The user''s first (given) name.';
COMMENT ON COLUMN platform_user.last_name IS 'The user''s last (family) name.';
COMMENT ON COLUMN platform_user.email IS 'The user''s Email address that is also their username.';
COMMENT ON COLUMN platform_user.encoded_password IS 'The user''s password encoded so not in plain text.';
COMMENT ON COLUMN platform_user.enabled IS 'If set the user is allowed to login, otherwise they cannot log in.';
COMMENT ON COLUMN platform_user.created_on IS 'When the user account was created.';
COMMENT ON COLUMN platform_user.modified_on IS 'When the user account was last modified.';

-- Represents an entire tutorial with modules and sessions.
CREATE TABLE IF NOT EXISTS tutorial (
    created_on TIMESTAMP WITH TIME ZONE DEFAULT NOW(),-- When the object was created.
    created_user_id UUID NOT NULL,
    identifier TEXT NOT NULL,-- A unique identifier associated with this tutorial.
    modified_on TIMESTAMP WITH TIME ZONE,-- When the object was last modified.
    overview TEXT,-- Tutorial overview.
    summary TEXT,-- The localized summary of the tutorial used when summarizing all tutorials in a view.
    title TEXT NOT NULL,-- The localized title of the tutorial.
    tutorial_id UUID PRIMARY KEY NOT NULL,
    FOREIGN KEY (created_user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE,
    UNIQUE (identifier)
);

CREATE TRIGGER tutorial_updatemodifiedon
BEFORE INSERT OR UPDATE ON tutorial
FOR EACH ROW EXECUTE PROCEDURE update_modified_on_column();

COMMENT ON TABLE tutorial IS 'Represents an entire tutorial with modules and sessions.';
COMMENT ON COLUMN tutorial.created_on IS 'When the object was created.';
COMMENT ON COLUMN tutorial.modified_on IS 'When the object was last modified.';
COMMENT ON COLUMN tutorial.identifier IS 'A unique identifier associated with this tutorial.';
COMMENT ON COLUMN tutorial.title IS 'The localized title of the tutorial.';
COMMENT ON COLUMN tutorial.summary IS 'The localized summary of the tutorial used when summarizing all tutorials in a view.';
COMMENT ON COLUMN tutorial.overview IS 'Tutorial overview.';

-- Represents a module within the tutorial. A module is a big partition of the tutorial.
CREATE TABLE IF NOT EXISTS tutorial_module (
    created_on TIMESTAMP WITH TIME ZONE DEFAULT NOW(),-- When the object was created.
    modified_on TIMESTAMP WITH TIME ZONE,-- When the object was last modified.
    module_id UUID PRIMARY KEY NOT NULL,
    number INT NOT NULL,-- Represents the module number.
    overview TEXT,-- Module overview.
    summary TEXT,-- The localized summary of the module used when sumarizing all modules of a tutorial.
    title TEXT NOT NULL,-- The localized title of the module.
    tutorial_id UUID NOT NULL,
    FOREIGN KEY (tutorial_id) REFERENCES tutorial(tutorial_id) ON DELETE CASCADE
);

CREATE TRIGGER tutorial_module_updatemodifiedon
BEFORE INSERT OR UPDATE ON tutorial_module
FOR EACH ROW EXECUTE PROCEDURE update_modified_on_column();

COMMENT ON TABLE tutorial_module IS 'Represents a module within the tutorial. A module is a big partition of the tutorial.';
COMMENT ON COLUMN tutorial_module.created_on IS 'When the object was created.';
COMMENT ON COLUMN tutorial_module.modified_on IS 'When the object was last modified.';
COMMENT ON COLUMN tutorial_module.number IS 'Represents the module number.';
COMMENT ON COLUMN tutorial_module.title IS 'The localized title of the module.';
COMMENT ON COLUMN tutorial_module.summary IS 'The localized summary of the module used when sumarizing all modules of a tutorial.';
COMMENT ON COLUMN tutorial_module.overview IS 'Module overview.';

-- Represents a session within a module. A session typically tries to teach a single concept.
CREATE TABLE IF NOT EXISTS session (
    created_on TIMESTAMP WITH TIME ZONE DEFAULT NOW(),-- When the object was created.
    discussion TEXT,-- The discussion section of the session.
    modified_on TIMESTAMP WITH TIME ZONE,-- When the object was last modified.
    module_id UUID NOT NULL,
    number INT NOT NULL,-- The session number.
    objective TEXT,-- The objective of the session.
    session_id UUID PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,-- The title of the session.
    FOREIGN KEY (module_id) REFERENCES tutorial_module(module_id) ON DELETE CASCADE
);

CREATE TRIGGER session_updatemodifiedon
BEFORE INSERT OR UPDATE ON session
FOR EACH ROW EXECUTE PROCEDURE update_modified_on_column();

COMMENT ON TABLE session IS 'Represents a session within a module. A session typically tries to teach a single concept.';
COMMENT ON COLUMN session.created_on IS 'When the object was created.';
COMMENT ON COLUMN session.modified_on IS 'When the object was last modified.';
COMMENT ON COLUMN session.title IS 'The title of the session.';
COMMENT ON COLUMN session.objective IS 'The objective of the session.';
COMMENT ON COLUMN session.discussion IS 'The discussion section of the session.';
COMMENT ON COLUMN session.number IS 'The session number.';

-- Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session.
CREATE TABLE IF NOT EXISTS exercise (
    created_on TIMESTAMP WITH TIME ZONE DEFAULT NOW(),-- When the object was created.
    exercise_id UUID PRIMARY KEY NOT NULL,
    modified_on TIMESTAMP WITH TIME ZONE,-- When the object was last modified.
    number INT NOT NULL,-- The exercise number.
    overview TEXT,-- Exercise overview.
    session_id UUID NOT NULL,
    FOREIGN KEY (session_id) REFERENCES session(session_id) ON DELETE CASCADE
);

CREATE TRIGGER exercise_updatemodifiedon
BEFORE INSERT OR UPDATE ON exercise
FOR EACH ROW EXECUTE PROCEDURE update_modified_on_column();

COMMENT ON TABLE exercise IS 'Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session.';
COMMENT ON COLUMN exercise.created_on IS 'When the object was created.';
COMMENT ON COLUMN exercise.modified_on IS 'When the object was last modified.';
COMMENT ON COLUMN exercise.number IS 'The exercise number.';
COMMENT ON COLUMN exercise.overview IS 'Exercise overview.';

-- An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise.
CREATE TABLE IF NOT EXISTS step (
    created_on TIMESTAMP WITH TIME ZONE DEFAULT NOW(),-- When the object was created.
    exercise_id UUID NOT NULL,
    instructions TEXT,-- Step content.
    modified_on TIMESTAMP WITH TIME ZONE,-- When the object was last modified.
    number INT NOT NULL,-- The step number.
    step_id UUID PRIMARY KEY NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id) ON DELETE CASCADE
);

CREATE TRIGGER step_updatemodifiedon
BEFORE INSERT OR UPDATE ON step
FOR EACH ROW EXECUTE PROCEDURE update_modified_on_column();

COMMENT ON TABLE step IS 'An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise.';
COMMENT ON COLUMN step.created_on IS 'When the object was created.';
COMMENT ON COLUMN step.modified_on IS 'When the object was last modified.';
COMMENT ON COLUMN step.number IS 'The step number.';
COMMENT ON COLUMN step.instructions IS 'Step content.';

CREATE TABLE IF NOT EXISTS platform_user_roles (
    user_id UUID NOT NULL,
    value INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES platform_user(user_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS tutorial_module_tutorial_idx ON tutorial_module(tutorial_id ASC);
CREATE INDEX IF NOT EXISTS step_exercise_idx ON step(exercise_id ASC);
CREATE INDEX IF NOT EXISTS exercise_session_idx ON exercise(session_id ASC);
CREATE INDEX IF NOT EXISTS platform_user_roles_user_idx ON platform_user_roles(user_id ASC);
CREATE INDEX IF NOT EXISTS session_module_idx ON session(module_id ASC);
