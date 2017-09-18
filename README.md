[![Build Status](https://travis-ci.org/mental-party/meconfig.svg?branch=master)](https://travis-ci.org/mental-party/meconfig)

# meconfig

## Spring Auto-Configurations

This package contains common configurations for Spring Web projects.

This package also has base business and presentation layer interfaces and implementations.

### Installation

* [jcenter](https://bintray.com/mental-soft/meparty/com.teammental.meconfig)

_Gradle Config_

<code>
dependencies {
    compile 'com.teammental:meconfig:1.0'
}
</code>

<hr/>

### Rest Handlers
* ValidationErrorRestHandler
* CRUD Handlers
    - EntityInsertExceptionRestHandler
    - EntityUpdateExceptionRestHandler
    - EntityDeleteExceptionRestHandler
    - EntityNotFoundExceptionRestHandler
    
    
### Business Layer
- BaseCrudService
    - BaseCrudServiceImpl
    
### Presentation Layer
- BaseController
- BaseCrudController