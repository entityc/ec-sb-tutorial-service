# Repository Classes

The classes in this directory are Repository classes as part of the Spring Framework.

| Class | Description |
|-------|-------------|
| `ExerciseRepository` | The repository class providing a layer of persistent storage for the `Exercise` entity. This entity can be described as: Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session. |
| `ModuleRepository` | The repository class providing a layer of persistent storage for the `Module` entity. This entity can be described as: Represents a module within the tutorial. A module is a big partition of the tutorial. |
| `SessionRepository` | The repository class providing a layer of persistent storage for the `Session` entity. This entity can be described as: Represents a session within a module. A session typically tries to teach a single concept. |
| `StepRepository` | The repository class providing a layer of persistent storage for the `Step` entity. This entity can be described as: An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise. |
| `TutorialRepository` | The repository class providing a layer of persistent storage for the `Tutorial` entity. This entity can be described as: Represents an entire tutorial with modules and sessions. |
| `UserRepository` | The repository class providing a layer of persistent storage for the `User` entity. This entity can be described as: Represents a user in the system. |

## Generated by

Template: `RepositoryPublisher`
