# JHipster Online

[![Build Status][github-actions-jhonline-image]][github-actions-url] [![Docker Pulls](https://img.shields.io/docker/pulls/jhipster/jhipster-online.svg)](https://hub.docker.com/r/jhipster/jhipster-online/)

JHipster Online is a Web application that allows to generate [JHipster applications](https://www.jhipster.tech/)
without installing JHipster on your machine.

This is an Open Source project ([Apache 2 license](https://github.com/jhipster/jhipster-online/blob/main/LICENSE.txt))
that powers the [https://start.jhipster.tech/](https://start.jhipster.tech/) website.

You can use [https://start.jhipster.tech/](https://start.jhipster.tech/) for free, but if you find a bug or need a specific
feature, you are welcome to contribute to the project. You can also choose to clone or fork the project, and have your own version
that is hosted within your company.

## Quick start guide

JHipster Online is a JHipster application, so you can follow the [JHipster documentation](https://www.jhipster.tech/) to
learn how to configure and set up JHipster Online.

This quick start guide uses the default configuration that comes with JHipster Online: please read
the next section for details on configuring the application.

- Install and run the front-end:

```
yarn install && yarn start
```

- Run the database:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

- Run the back-end:

```
./mvnw
```

## Specific configuration

This section covers what is specific to JHipster Online over a normal JHipster application.

For standard JHipster configuration, the [JHipster common application properties](https://www.jhipster.tech/common-application-properties/)
will probably be very useful.

### JHipster installation and execution

JHipster Online generates a JHipster application by running the `jhipster` command line. In order for that
command line to work, you need to have JHipster installed on your machine.

We recommend you use the "Yarn installation" from the official [JHipster installation documentation](https://www.jhipster.tech/installation/).

If you need more configuration options for running the JHipster command, you can modify:

- the location of the `jhipster` command
- the timeout value for that command (the default is 120 seconds - please note that on our production server a generation usually
  takes 5 to 6 seconds)
- the temporary folder in which the application will be generated (`/tmp` by default)

Those are customized using the Spring Boot `application-*.yml` files as usual, for example:

```
application:
    jhipster-cmd:
        cmd: /usr/local/bin/jhipster
        timeout: 60
    tmp-folder: /tmp
```

### Database configuration

JHipster Online works with a MySQL database, that is configured in the usual `application-*.yml` Spring Boot configuration
files, using the standard `spring.datasource` keys.

### Security

JHipster Online uses JWT to secure the application. For a production application, it is therefore **mandatory** that:

- The `jhipster.security.authentication.jwt.key` is configured, and that key is stored securely (**not** commited in your application's Git repository).
  We recommend to configure it as an environment variable on your server, or in a specific Spring Boot `application.yml` file that is stored
  in your application's folder on your production server (which is our configuration on the official [JHipster Online website](https://start.jhipster.tech/)).
- The application is only available through HTTPS. You can configure it using Spring Boot (please read the comments in the `application-prod.yml` file), or
  using an Apache 2 HTTP server with Let's Encrypt on front of your application (which is our configuration on the official [JHipster Online website](https://start.jhipster.tech/)).

### Mail

E-mails are used to validate users' e-mail addresses or to send "forgotten password" e-mails. They are disabled by default,
but it might be a good idea to configure them once the application is in production.

To configure e-mail sending, you need to configure the `jhipster.mail` keys (see [JHipster common application properties](https://www.jhipster.tech/common-application-properties/)),
and the Spring Boot standard `spring.mail` keys.

When running the app with the `dev` profile, make sure to start the development mail server with:

```
docker-compose -f src/main/docker/mailserver.yml up -d
```

You can view the mails sent by JHipster with the MailHog UI at [http://localhost:8025](http://localhost:8025).

### GitHub configuration

GitHub is configured using the `application.github` keys in the `application-*.yml` configuration files.

JHipster Online can work on the public GitHub instance on [https://github.com](https://github.com) as well
as any private instance of GitHub Enterprise that is configured inside your company.

JHipster Online has to be configured as an "OAuth App": create a `jhipster` organization,
and go to that organization's "Settings > Developer Settings > OAuth Apps" to create a new "OAuth App" with
the required credentials. This will allow JHipster Online to create applications and pull requests on your
behalf. JHipster Online uses `https://your-jhipster-online-url/api/github/callback` as callback endpoint.

JHipster Online also needs to have a specific "JHipster Bot" user configured, like the  
[https://github.com/jhipster-bot](https://github.com/jhipster-bot) used by the official [JHipster Online website](https://start.jhipster.tech/).

Here is the final configuration, that should be set up inside the `application-dev.yml` file for
development, and inside the `application-prod.yml` file for production.

```
application:
    github:
        host: https://github.com # The GitHub to connect to (by default: the public GitHub instance)
        client-id: XXX # The OAuth Client ID of the application on GitHub
        client-secret: XXX # The OAuth Client secret of the application on GitHub
```

### GitLab configuration

Similarly to GitHub, your GitLab configuration must be placed in your `application-*.yml` using the `application.gitlab`
keys.

JHipster Online can work on the public GitLab instance on [https://gitlab.com](https://gitlab.com) as well
as any private instance of GitLab that is configured inside your company.

JHipster Online needs to have a specific "JHipster Bot" user configured: create that user (if you have your own private instance, you can call it
`jhipster-bot`, otherwise choose the name you like), and log in using that user.

Once logged in, the required API credentials can be created by going to "Settings > Applications > Add new application".
Create a new application:

- Its name is `jhipster`
- The redirect URI is `https://your-jhipster-online-url/api/gitlab/callback`
- It has the `api` and `read_user` scopes

Save that new application and store safely the `Application Id` and `Secret` values, so you can use them to configure
the `application-*.yml` files.

Here is the final configuration, that should be set up inside the `application-dev.yml` file for
development, and inside the `application-prod.yml` file for production.

```
application:
    gitlab:
        host: https://gitlab.com # The GitLab to connect to. The main public GitLab instance is default here.
        client-id: XXX # Your GitLab application Id
        client-secret: XXX # Your GitLab application secret
        redirect-uri: XXX   # The URI where the user will be redirected after GitLab authentication. This URI
                            # must be registered in you GitLab application callback URLs
```

## Building for production

To generate a production build, like any normal JHipster application, please run:

```
./mvnw -Pprod clean package
```

### Using Docker

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
docker build -t jhonline .
```

Then run:

```
docker compose -f src/main/docker/app.yml up -d
```

## Deployment to Google App Engine (GAE) Flexible Environment

- The project can be deployed to [GAE flexible environment](https://cloud.google.com/appengine/docs/flexible/) by simply [creating a tagged release](https://github.com/jhipster/jhipster-online/releases).

- Creating a new release triggers the [deploy.yml GitHub Actions workflow](https://github.com/jhipster/jhipster-online/blob/main/.github/workflows/deploy.yml).

- The above workflow includes a Docker build and pushes the image to [Google Container Registry](https://cloud.google.com/container-registry/). [Cloud Monitoring](https://cloud.google.com/monitoring) and [Cloud Logging](https://cloud.google.com/logging) are used for
  obtaining application logs and monitoring.

#### Steps for Deployment

- Increment the version numbers in [`pom.xml`](https://github.com/jhipster/jhipster-online/blob/d61bdbc3b6354516756c57626455be180eb4352e/pom.xml#L8) and [`package.json`](https://github.com/jhipster/jhipster-online/blob/d61bdbc3b6354516756c57626455be180eb4352e/package.json#L3).
- Create a tagged release. All you have to do is got to the [releases section](https://github.com/jhipster/jhipster-online/releases)
  and edit the draft created by `github-actions`.

## Help and contribution to the project

Please note that this project is part of the [JHipster organization](https://github.com/jhipster) and it follows the rules
of the [JHipster project](https://github.com/jhipster/generator-jhipster).

### If you have an issue, a bug or a feature request

Please follow our [contribution guide](https://github.com/jhipster/jhipster-online/blob/main/CONTRIBUTING.md).

### If you have a question or need help

You should [post it on Stack Overflow using the "jhipster" tag](https://stackoverflow.com/questions/tagged/jhipster?sort=newest).

### Code of conduct

We have the same code of conduct as the main JHipster project:
[JHipster code of conduct](https://github.com/jhipster/jhipster-online/blob/main/CODE_OF_CONDUCT.md).

[github-actions-jhonline-image]: https://github.com/jhipster/jhipster-online/workflows/Application%20CI/badge.svg
[github-actions-url]: https://github.com/jhipster/jhipster-online/actions
