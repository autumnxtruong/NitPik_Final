 
## NitPik Photo Management App
_________________________


NikPik is a photo-management web application equipped with the necessary features users need 
for securely managing and sharing photos. Basic features include allowing users to easily manage 
(upload, download, edit, or delete) their media. For organization and categorization, users can 
create customizable tags and tag their media for easier searchability. Such features make NitPik 
perfect for those who are nitpicky about how they want to organize their photos.

## User Stories
_________________________

- Person is able to sign up and if not a User.
- User is able to login with email and password to access the account.

  **CRUD Functions for Photos:**

- User can upload photo(s) from local system
- User can upload photo from url
- User is able to view all uploaded Photos that they have access to.
- User is able to edit Photo that they own
- User is able to delete Photo
- User is able to share Photo with another User
- User is able to add and remove Tags.

## Technologies Used
_________________________

- Spring Boot
- Maven
- MariaDB
- Java, HTML,CSS,JS
- Spring Security, Spring Data JPA, Spring Web, Lombok, Java Mail Sender, Cloudinary

 
##  Description of code
_________________________

- Annotaions folder  contains messages in case of failure <br>
- Controllers folder contain controllers which process requests coming from client side <br>
- Domain folder contains all our entities <br>
- Security folder is used for spring security <br>
- messageCreation is used to create the message that will be sent by email during 
- Activation of the account <br>
- Services will describe the logic of all functionalities <br>
- Validators it contains classes that will validate the inputs of the user