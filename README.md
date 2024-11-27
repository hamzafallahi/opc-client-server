# SmartFactory OPC Module

**SmartFactory OPC Module** is a client-server module designed to collect, clean, store, and analyze data from an OPC server for AI-based predictions. This module simulates an OPC server using real-time weather data from the OpenWeather API and implements various security measures to ensure secure communication and data integrity.

## Features

- Simulates an OPC server using OpenWeather API data.
- Implements server-side security measures, including identity validation and certificate management.
- Allows for the monitoring, subscription, and modification of OPC node variables.
- Designed for future integration with AI prediction models.

## Version History

### Version 0.1
- **Feature**: Created an unsecured OPC server with basic endpoints.
- **Details**: Uses the OpenWeather API to simulate weather data, mimicking the behavior of an actual OPC server.

### Version 0.2
- **Feature**: Added optional server-side security measures.
- **Details**:
  - **Username Identity Validator**: Validates clients based on username and password.
  - **X509 Identity Validator**: Uses X.509 certificates for client authentication and secure communication.
  - **Certificate Management**: Manages server and client certificates, ensuring only trusted connections.
  - **Security Policies**: Implements `SecurityPolicy.Basic256` and `SecurityPolicy.Basic256Sha256` along with `MessageSecurityMode.Sign` and `MessageSecurityMode.SignAndEncrypt` to protect communication.

### Version 0.3
- **Feature**: Added node variables from weather data.
- **Details**: Variables are created for each weather parameter, with the ability to update them based on frequency or change, using the Prosys OPC UA client.

### Version 0.4
- **Feature**: Enhanced variable management.
- **Details**: Variables can now be subscribed to, monitored, or edited, with flexible update triggers based on frequency or change.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 11 or later.
- **Maven**: For building the project.
- **Prosys OPC UA Client**: To interact with the OPC server.
- **OpenWeather API Key**: For accessing weather data (optional).

### Installation

1. **Clone the repository**:
    ```bash
    git clone http://git.addixo.io/sf-group/smartfactory-opc-module.git
    git checkout devhamza
    cd smartfactory-opc-module
    ```

2. **Build the project using Maven**:
    ```bash
    mvn clean install
    ```

3. **Run the OPC Server**:


### Usage

- **Start the OPC server**: Follow the instructions above.
- **Interact using Prosys OPC UA Client**:
  - Connect to the server using the provided endpoint.
  - Browse and interact with the OPC node variables.

### Security Configuration (optional)

- **Username Identity Validation**: Modify the list of valid usernames and passwords in the configuration file.
- **X509 Identity Validation**: Manage certificates in the keystore (`keystore.pfx`). New certificates can be generated using the `KeyStoreLoader` class.

## Contributing

If you'd like to contribute to the project, please fork the repository and create a pull request. For major changes, please open an issue to discuss what you would like to change.

## License

This project is licensed under the "Placeholder" License For Momsoft - see the "PlaceHolder" file for details.
