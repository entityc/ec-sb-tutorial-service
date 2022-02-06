# Exercise

Represents an exercise within a session. A session typically only has one exercise but it can have more than one if the session is big. An exercise gives the student some hands on experience with the material covered by its session.

|Field|Type|Description|
|:---|:---:|:---|
|`id`|UUID|*Primary Key*|
|`createdOn`|Date|When the object was created.|
|`modifiedOn`|Date|When the object was last modified.|
|`number`|int|The exercise number.|
|`overview`|String|Exercise overview.|

### Relationships

This entity has the following relationships with other entities:

|Type|Other Entity|Relationship Name|Description|
|:---:|:---|:---|:---|
|one|`Session`|`session`|The session to which this exercise belongs.|
|many|`Step`|`steps`|The steps of this exercise.|
