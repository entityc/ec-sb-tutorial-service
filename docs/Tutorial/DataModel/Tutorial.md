# Tutorial

Represents an entire tutorial with modules and sessions.

|Field|Type|Description|
|:---|:---:|:---|
|`id`|UUID|*Primary Key*|
|`createdOn`|Date|When the object was created.|
|`identifier`|String|A unique identifier associated with this tutorial.|
|`modifiedOn`|Date|When the object was last modified.|
|`overview`|String|Tutorial overview.|
|`summary`|String|The localized summary of the tutorial used when summarizing all tutorials in a view.|
|`title`|String|The localized title of the tutorial.|

### Relationships

This entity has the following relationships with other entities:

|Type|Other Entity|Relationship Name|Description|
|:---:|:---|:---|:---|
|many|`Module`|`modules`|The modules of a tutorial.|
|one|`User`|`createdUser`|The user that created the tutorial.|
