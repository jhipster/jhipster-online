# JHipster Online

JHipster Online is a Web application that allows to generate [JHipster applications](https://www.jhipster.tech/) 
without installing JHipster on your machine.

This is an Open Source project ([Apache 2 license](https://github.com/jhipster/jhipster-online/blob/master/LICENSE.txt))
that powers the [https://start.jhipster.tech/](https://start.jhipster.tech/) website.

You can use [https://start.jhipster.tech/](https://start.jhipster.tech/) for free, but if you find a bug or need a specific
feature, you are welcome to contribute to the project. You can also choose to clone or fork the project, and have your own version
that is hosted within your company.

## Quickstart guide

JHipster Online is a JHipster application, so you can follow the [JHipster documentation](https://www.jhipster.tech/) to
learn how to configure and set up JHipster Online. In that documentation, the 
[JHipster common application properties](https://www.jhipster.tech/common-application-properties/) will probably be very
useful.

- Install and run the front-end:


    yarn install && yarn start


- Run the database:


    docker-compose -f src/main/docker/mysql.yml up -d


- Run the back-end:


    ./mvnw

### Building for production

To generate a production build, like any normal JHipster application, please run:

    ./mvnw -Pprod clean package

### Using Docker
You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod dockerfile:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d

## Specific configuration

This section covers what is specific to JHipster Online over a normal JHipster application.

### Database configuration

JHipster Online works with a MySQL database, that is configured in the usual `application-*.yml` Spring Boot configuration
files, using the standard `spring.datasource` keys.

### Security

JHipster Online uses JWT to secure the application. For a production application, it is therefore **mandatory** that:

- The `jhipster.security.authentication.jwt.key` is configured, and that key is stored securely (**not** commited in your application's Git repository).
We recommend to configure it as an environnement variable on your server, or in a specific Spring Boot `application.yml` file that is stored
in your application's folder on your production server (which is our configuration on the official [JHipster Online website](https://start.jhipster.tech/)).
- The application is only available through HTTPS. You can configure it using Spring Boot (please read the comments in the `application-prod.yml` file), or 
using an Apache 2 HTTP server with Let's Encrypt on front of your application (which is our configuration on the official [JHipster Online website](https://start.jhipster.tech/)).

### Mail

E-mails are used to validate users' e-mail addresses or to send "forgotten password" e-mails. They are disabled by default, 
but it might be a good idea to configure them once the application is in production.

To configure e-mail sending, you need to configure the `jhipster.mail` keys (see [JHipster common application properties](https://www.jhipster.tech/common-application-properties/)),
and the Spring Boot standard `spring.mail` keys.

### GitHub configuration

GitHub is configured using the `application.github` keys in the `application-*.yml` configuration files.

JHipster Online can work on the public GitHub instance on [https://github.com](https://github.com) as well
as any private instance of GitHub Enterprise that is configured inside your company.

JHipster Online has to be configured as an "OAuth App": create a `jhipster` organization,
and go to that organization's "Settings > Developer Settings > OAuth Apps" to create a new "OAuth App" with
the required credentials. This will allow JHipster Online to create applications and pull requests on your
behalf.

JHipster Online also needs to have a specific "JHipster Bot" user configured, like the  
[https://github.com/jhipster-bot](https://github.com/jhipster-bot) used by the official [JHipster Online website](https://start.jhipster.tech/).
In order for JHipster Online to use that bot, it will need its OAuth token: log in as the "JHipster Bot" user, and go to 
"Settings > Developer Settings > Personal access tokens" and generate a new token.

Here is the final configuration, that should be set up inside the `application-dev.yml` file for 
development, and inside the `application-prod.yml` file for production.

```
application:
    github:
        host: https://github.com # The GitHub to connect to (by default: the public GitHub instance)
        client-id: XXX # The OAuth Client ID of the application on GitHub
        client-secret: XXX # The OAuth Client secret of the application on GitHub
        jhipster-bot-oauth-token: XXX # The "personal access token" of the JHipster Bot
```

### GitLab configuration

TODO

## Help and contribution to the project

Please note that this project is part of the [JHipster organization](https://github.com/jhipster) and it follows the rules 
of the [JHipster project](https://github.com/jhipster/generator-jhipster).

### If you have an issue, a bug or a feature request

Please follow our [contribution guide](https://github.com/jhipster/jhipster-online/blob/master/CONTRIBUTING.md).

### If you have a question or need help

You should [post it on Stack Overflow using the "jhipster" tag](https://stackoverflow.com/questions/tagged/jhipster?sort=newest).

### Code of conduct

We have the same code of conduct as the main JHipster project:
[JHipster code of conduct](https://github.com/jhipster/jhipster-online/blob/master/CODE_OF_CONDUCT.md).
