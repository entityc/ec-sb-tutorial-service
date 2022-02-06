# Controller Classes

The classes in this directory are Controller classes as part of the Spring Framework.

| Class | Description |
|-------|-------------|
| `ExerciseController` | The controller class that implements endpoints for the `Exercise` entity. This entity can be described as: Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session. |
| `ModuleController` | The controller class that implements endpoints for the `Module` entity. This entity can be described as: Represents a module within the tutorial. A module is a big partition of the tutorial. |
| `SessionController` | The controller class that implements endpoints for the `Session` entity. This entity can be described as: Represents a session within a module. A session typically tries to teach a single concept. |
| `StepController` | The controller class that implements endpoints for the `Step` entity. This entity can be described as: An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise. |
| `TutorialController` | The controller class that implements endpoints for the `Tutorial` entity. This entity can be described as: Represents an entire tutorial with modules and sessions. |
| `UserController` | The controller class that implements endpoints for the `User` entity. This entity can be described as: Represents a user in the system. |

## Generated by

Template: `ControllerPublisher`
