# Tutorial Data Model

This module contains entities for building a tutorial system.


# Entities

|Name|Description|
|:---|:---|
|[`Exercise`](Exercise.md)|Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session.|
|[`Module`](Module.md)|Represents a module within the tutorial. A module is a big partition of the tutorial.|
|[`Session`](Session.md)|Represents a session within a module. A session typically tries to teach a single concept.|
|[`Step`](Step.md)|An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise.|
|[`Tutorial`](Tutorial.md)|Represents an entire tutorial with modules and sessions.|

