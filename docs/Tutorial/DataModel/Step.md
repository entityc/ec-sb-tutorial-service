# Step

An exercise is broken down into smaller steps where a single step has the user perform a small task as part of the exercise.

|Field|Type|Description|
|:---|:---:|:---|
|`id`|UUID|*Primary Key*|
|`createdOn`|Date|When the object was created.|
|`instructions`|String|Step content.|
|`modifiedOn`|Date|When the object was last modified.|
|`number`|int|The step number.|

### Relationships

This entity has the following relationships with other entities:

|Type|Other Entity|Relationship Name|Description|
|:---:|:---|:---|:---|
|one|`Exercise`|`exercise`|The exercise to which this step belongs.|
