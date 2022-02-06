# User

Represents a user in the system.


## Create User

|Method|Path|
|:---:|:---|
|POST|`/api/ectutorials/user`|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`email`|The user's Email address that is also their username.|
|`enabled`|If set the user is allowed to login, otherwise they cannot log in.|
|`encodedPassword`|The user's password encoded so not in plain text.|
|`firstName`|The user's first (given) name.|
|`lastName`|The user's last (family) name.|
|`roles`|The roles assigned to a user.|

## Get User List

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/user?start={start}&limit={limit}`|

Where:

|Param|Description|
|:---|:---|
|`start`|The starting index in the full result set.|
|`limit`|The number of results to return.|

The response JSON is simply an array of the same User objects as returned by the Get User by ID endpoint.

## Get User by ID

|Method|Path|
|:---:|:---|
|GET|`/api/ectutorials/user/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `User` object to be returned.|

The body of the response is JSON containing the following fields:

|Field|Description|
|:---|:---|
|`userId`|_Primary Key_|
|`createdOn`|When the user account was created.|
|`email`|The user's Email address that is also their username.|
|`enabled`|If set the user is allowed to login, otherwise they cannot log in.|
|`encodedPassword`|The user's password encoded so not in plain text.|
|`firstName`|The user's first (given) name.|
|`lastName`|The user's last (family) name.|
|`modifiedOn`|When the user account was last modified.|
|`roles`|The roles assigned to a user.|

## Update User

|Method|Path|
|:---:|:---|
|PUT|`/api/ectutorials/user/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique ID of the `User` object to be updated.|

The body of the POST is JSON containing only the following fields:

|Field|Description|
|:---|:---|
|`email`|The user's Email address that is also their username.|
|`enabled`|If set the user is allowed to login, otherwise they cannot log in.|
|`encodedPassword`|The user's password encoded so not in plain text.|
|`firstName`|The user's first (given) name.|
|`lastName`|The user's last (family) name.|
|`roles`|The roles assigned to a user.|

## Delete User by ID

|Method|Path|
|:---:|:---|
|DELETE|`/api/ectutorials/user/{id}`|

Where:

|Param|Description|
|:---|:---|
|`id`|The unique id of the User object to be deleted.|



