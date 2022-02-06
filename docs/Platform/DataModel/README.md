# Platform Data Model

This module contains entities associated with a base platform on which functionality is built.


# Enums

## ROLE

Represents a level of security in the system.

|Item|Description|
|:---|:---|
|`STUDENT`|Is only allowed to view tutorials.|
|`INSTRUCTOR`|Is allowed to view, modify and create new tutorials.|
|`ADMINISTRATOR`|Is allowed to do what the Instructor can do but also change the role of users.|

# Entities

|Name|Description|
|:---|:---|
|[`User`](User.md)|Represents a user in the system.|

