# grails-utilities

A collection of goodies for Grails.

## Uncertain

A wrapper class that helps to separate business logic and controller logic.
Inspired by Java `Optional` and the `Maybe` types in other languages,
it can hold a class instance or an error map but not both.

### How to use

In your service create a method to retrieve the desired object.
This operation can either succeed (the object is returned) or fail (an error map is returned).

```groovy
import com.vincoorbis.grails.Uncertain

...

Uncertain<UserProfile> getValidUserProfile(User userInstance) {
    UserProfile userProfileInstance = UserProfile.findByUser(userInstance)

    if(!userProfileInstance) return Uncertain.failure(userProfile:'notFound')
    if(!userProfileInstance.validate()) return Uncertain.failure(userProfile:'invalid')
    return Uncertain.success(userProfileInstance)
}
```

In your controller, receive the `Uncertain` instance and just check if it's valid or not, 
leave the complex validations and error generation in the service.

```groovy
import com.vincoorbis.grails.Uncertain

...

Uncertain<UserProfile> userProfile = demoService.getValidUserProfile(user)
if(!userProfile) {
    response.status = 412
    render ([errors:userProfile.errors] as JSON)
    return
}

demoService.doSomethingWirh(userProfile.instance)
```