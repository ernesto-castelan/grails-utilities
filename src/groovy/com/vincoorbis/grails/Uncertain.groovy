package com.vincoorbis.grails

class Uncertain<T> {

    private T instance
    private Map errors

    /* Groovy does not respect the private keyword. This is a way to disable the named argument constructor
     * and somewhat prevent accidental instantiation with the default constructor. Don't judge me.
     */
    private Uncertain(Boolean b) {}

    static <T> Uncertain<T> success(T instance) {
        if(instance == null) throw new IllegalArgumentException("Instance can't be null")

        Uncertain<T> result = new Uncertain<T>(true)
        result.instance = instance
        result.errors = null

        result
    }

    static <T> Uncertain<T> failure(Map errors = [:]) {
        if (errors == null) throw new IllegalArgumentException("Errors can't be null")

        Uncertain<T> result = new Uncertain<T>(true)
        result.instance = null
        result.errors = errors

        result
    }

    // Tests instance strictly against null
    static <T> Uncertain<T> testNull(T instance, Map errors) {
        if(instance == null) failure(errors)
        else success(instance)
    }

    // Tests instance using The Groovy Truth (TM)
    static <T> Uncertain<T> testTruth(T instance, Map errors) {
        if(!instance) failure(errors)
        else success(instance)
    }

    T getInstance() {
        if(instance == null) throw new NullPointerException("Can't access a null instance")
        instance
    }

    Map getErrors() {
        if(errors == null) throw new NullPointerException("Can't access a null error map")
        errors
    }

    boolean isNull() {
        instance == null
    }

    boolean asBoolean() {
        instance
    }
}
