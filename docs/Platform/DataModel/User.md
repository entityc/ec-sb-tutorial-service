# User

Represents a user in the system.

|Field|Type|Description|
|:---|:---:|:---|
|`id`|UUID|*Primary Key*|
|`createdOn`|Date|When the user account was created.|
|`email`|String|The user's Email address that is also their username.|
|`enabled`|boolean|If set the user is allowed to login, otherwise they cannot log in.|
|`encodedPassword`|String|The user's password encoded so not in plain text.|
|`firstName`|String|The user's first (given) name.|
|`lastName`|String|The user's last (family) name.|
|`modifiedOn`|Date|When the user account was last modified.|
|`roles`|Role|The roles assigned to a user.|

### Relationships

This entity has the following relationships with other entities:

|Type|Other Entity|Relationship Name|Description|
|:---:|:---|:---|:---|
|many|`UserRoles`|`roles`||
