# securitySample
Security sample project using JWT (JSON Web Token)

Postman requests available at src/main/resources/static/.

#### Operations

    - Get Employees (GET)
    - Get Users (GET)
    - Get an Employee (GET)
    - Get an User (GET)
    - Login (POST)
    - Create User (POST)
    
#### Project Structure

- FrontendSample // https://github.com/apokochito/frontendSample
- BackendSample // https://github.com/apokochito/backendSample
- SecuritySample // This repository
- ValidationJobSample // https://github.com/apokochito/validationJobSample
    
#### Description
	
##### 1 Scenario (master branch)
	
	This project supports authentication and authorization, than means once the user is logged in, each subsequent request will include the JWT, allowing the user to access routes, services, and resources that are permitted with that token.
	
	Authorization - JWT Filter - Users credentials
	
	Authentication - JWT Filter - Validate contatining JWTs
	
	Whenever the user wants to access a protected route or resource, the user agent should send the JWT, typically in the Authorization header using the Bearer schema.
	
##### 2 Scenario (info branch)
	
    Information Exchange
    
	JWTs can be signed—for example, using public/private key pairs—you can be sure the senders are who they say they are. Additionally, as the signature is calculated using the header and the payload, you can also verify that the content hasn't been tampered with.
    
##### Sections

    Header - Token type (JWT) and Algorithm (HMAC, SHA, RSA)
    
    Payload - Claims (registered, public, or private) // Claims are statements about an entity (typically, the user) and additional data.
    
    Signature - Take the encoded header, the encoded payload, a secret, the algorithm specified in the header, and sign that. // The signature is used to verify the message wasn't changed along the way, and, in the case of tokens signed with a private key, it can also verify that the sender of the JWT is who it says it is.
    
##### JWT vs OAUT2: JWT is just a token format, OAuth 2.0 is a protocol (that may use a JWT as a token format.
  
