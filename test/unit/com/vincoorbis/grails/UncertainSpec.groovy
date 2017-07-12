package com.vincoorbis.grails

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class UncertainSpec extends Specification {

    void "'success' method creates a succesfull instance"() {
        when:"Using the success method"
            Uncertain<String> result = Uncertain.success("hello")
        then:"The instance is correct"
            result.@instance == "hello"
            result.@errors == null
    }

    void "'success' method with falsy param creates a succesfull instance"() {
        when:"Using the success method"
            Uncertain<String> result = Uncertain.success("")
        then:"The instance is correct"
            result.@instance == ""
            result.@errors == null
    }

    void "'success' method with null parameter raises an exception"() {
        when:"Using the success method"
            Uncertain<String> result = Uncertain.success(null)
        then:"An exception is raised"
            thrown IllegalArgumentException
    }

    void "'failure' method creates an unsuccesfull instance"() {
        when:"Using the failure method"
            Uncertain<String> result = Uncertain.failure([property:"error.code"])
        then:"The instance is correct"
            result.@instance == null
            result.@errors == [property:"error.code"]
    }

    void "'failure' method with null parameter raises an exception"() {
        when:"Using the failure method"
            Uncertain<String> result = Uncertain.failure(null)
        then:"An exception is raised"
            thrown IllegalArgumentException
    }

    void "'testNull' method evaluates correctly"() {
        when:"Using the testNull method"
            Uncertain<String> result = Uncertain.testNull(value, [property:"error.code"])
        then:"The instance is correct"
            result.@instance == expectedInstance
            result.@errors == expectedErrors
        where:
            value       ||expectedInstance  |expectedErrors
            "hello"     ||"hello"            |null
            ""          ||""                |null
            null        ||null              |[property:"error.code"]
    }

    void "'testNull' method with null parameters raises an exception"() {
        when:"Using the testNull method"
            Uncertain<String> result = Uncertain.testNull(null, null)
        then:"An exception is raised"
            thrown IllegalArgumentException
    }

    void "'testTruth' method evaluates correctly"() {
        when:"Using the testTruth method"
            Uncertain<String> result = Uncertain.testTruth(value, [property:"error.code"])
        then:"The instance is correct"
            result.@instance == expectedInstance
            result.@errors == expectedErrors
        where:
            value       ||expectedInstance  |expectedErrors
            "hello"     ||"hello"            |null
            ""          ||null              |[property:"error.code"]
            null        ||null              |[property:"error.code"]
    }

    void "'testTruth' method with null parameters raises an exception"() {
        when:"Using the testTruth method"
            Uncertain<String> result = Uncertain.testTruth(null, null)
        then:"An exception is raised"
            thrown IllegalArgumentException
    }

    void "'isNull' method evaluates correctly"() {
        given:"An Uncertain instance"
            Uncertain<String> result = Uncertain.testNull(value, [property:"error.code"])
        expect:"The correct value"
            result.isNull() == expectedResult
        where:
            value       ||expectedResult
            "hello"     ||false
            ""          ||false
            null        ||true
    }

    void "'asBoolean' method evaluates correctly"() {
        given:"An Uncertain instance"
            Uncertain<String> result = Uncertain.testNull(value, [property:"error.code"])
        expect:"The correct value"
            !result == expectedResult
        where:
            value       ||expectedResult
            "hello"     ||false
            ""          ||true
            null        ||true
    }

    void "'instance' getter with null instance raises an exception"() {
        given:"An Uncertain instance"
            Uncertain<String> result = Uncertain.failure([property:"error.code"])
        when:"Accessing the instance property"
            result.instance
        then:"An exception is raised"
            thrown NullPointerException
    }

    void "'errors' getter with null error map raises an exception"() {
        given:"An Uncertain instance"
            Uncertain<String> result = Uncertain.success("hello")
        when:"Accessing the instance property"
            result.errors
        then:"An exception is raised"
            thrown NullPointerException
    }
}
