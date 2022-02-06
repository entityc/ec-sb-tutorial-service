# Module

Represents a module within the tutorial. A module is a big partition of the tutorial.

|Field|Type|Description|
|:---|:---:|:---|
|`id`|UUID|*Primary Key*|
|`createdOn`|Date|When the object was created.|
|`modifiedOn`|Date|When the object was last modified.|
|`number`|int|Represents the module number.|
|`overview`|String|Module overview.|
|`summary`|String|The localized summary of the module used when sumarizing all modules of a tutorial.|
|`title`|String|The localized title of the module.|

### Relationships

This entity has the following relationships with other entities:

|Type|Other Entity|Relationship Name|Description|
|:---:|:---|:---|:---|
|one|`Tutorial`|`tutorial`|The tutorial to which this module belongs.|
|many|`Session`|`sessions`|The sessions of this module.|
