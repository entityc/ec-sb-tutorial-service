# Session

Represents a session within a module. A session typically tries to teach a single concept.

|Field|Type|Description|
|:---|:---:|:---|
|`id`|UUID|*Primary Key*|
|`createdOn`|Date|When the object was created.|
|`discussion`|String|The discussion section of the session.|
|`modifiedOn`|Date|When the object was last modified.|
|`number`|int|The session number.|
|`objective`|String|The objective of the session.|
|`title`|String|The title of the session.|

### Relationships

This entity has the following relationships with other entities:

|Type|Other Entity|Relationship Name|Description|
|:---:|:---|:---|:---|
|one|`Module`|`module`|The module to which this session belongs.|
|many|`Exercise`|`exercises`|The exercises of this session.|
