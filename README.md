# HiltSample
Hilt Dependency Injection

- its wrapper around Dagger2
- Hilt uses under the hood.
- Reduced boilerplate
- Decoupled build dependencies
- Simplified configuration
- Improved testing
-  Standardized components

- Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.

### Adding dependencies
``` buildscript {
      dependencies {
        
          classpath 'com.google.dagger:hilt-android-gradle-plugin:2.38.1'
        }
    }
```

- Added dependency and plugin in `bnuild.gradle` aap side

```
       plugins {
        id 'kotlin-kapt'
        id 'dagger.hilt.android.plugin'
      }

      android {
          ...
      }

      dependencies {
        implementation 'com.google.dagger:hilt-android:2.40.5'
        kapt 'com.google.dagger:hilt-compiler:2.40.5'

        // For instrumentation tests
        androidTestImplementation  'com.google.dagger:hilt-android-testing:2.40.5'
        kaptAndroidTest 'com.google.dagger:hilt-compiler:2.40.5'

        // For local unit tests
        testImplementation 'com.google.dagger:hilt-android-testing:2.40.5'
        kaptTest 'com.google.dagger:hilt-compiler:2.40.5'
      }

      kapt {
       correctErrorTypes true
      }
```


### Notes
- We have field injection and constructor injection
- We have to use the constructor injection over the field injection
    - Why ?
        - When you use constructor injection that means , you provide the dependency at the time of creating the  instance of object , so we know exactly what this object required , or its east to test also we can mock the dependency.

    ```
        // field injection
         @Inject
         lateinit var Someclass:Someclass

// constructor injection passing through the constructor
         class Someclass
         @Inject
         constructor(private someOtherClass:SomeOtherClass){
           fun functionOne():String="This one from SomeClass"

           fun getSomeOtherClass():String= someOtherClass.functionOne()

         }

         class SomeOtherClass
         @Inject
         constructor(){
            fun functionOne():String="This one from SomeOtherClass"
         }
    ```
#### Hilt Scoping

- never define or instantiate Dagger components directly. Instead
- have predefined components
- Hilt comes with a built-in set of components (and corresponding scope annotations) that are automatically integrated into the various lifecycles of an Android application
 
```   
    Component           	    Injector for
    SingletonComponent	            Application
    ViewModelComponent	            ViewModel
    ActivityComponent	            Activity
    FragmentComponent	            Fragment
    ViewComponent	                View
    ViewWithFragmentComponent	    View with @WithFragmentBindings
    ServiceComponent	            Service
```   

```
        Component	                Scope	                        Created at	                    Destroyed at
        SingletonComponent	        @Singleton	                Application#onCreate()	        Application#onDestroy()
        ActivityRetainedComponent	@ActivityRetainedScoped	    Activity#onCreate()1	        Activity#onDestroy()1
        ViewModelComponent	        @ViewModelScoped	        ViewModel created	            ViewModel destroyed
        ActivityComponent	        @ActivityScoped	            Activity#onCreate()         	Activity#onDestroy()
        FragmentComponent	        @FragmentScoped	            Fragment#onAttach()	            Fragment#onDestroy()
        ViewComponent	            @ViewScoped	                View#super()	                View destroyed
        ViewWithFragmentComponent	@ViewScoped	                View#super()	                View destroyed
        ServiceComponent	        @ServiceScoped	Service#onCreate()	Service#onDestroy()
       
```

### Scoped vs unscoped bindings

- By default, all bindings in Dagger are “unscoped”. This means that each time the binding is requested, Dagger will create a new instance of the binding.
- A scoped binding will only be created once per instance of the component it’s scoped to, and all requests for that binding will share the same instance.

```aidl

            // This binding is "unscoped".
        // Each request for this binding will get a new instance.
        class UnscopedBinding @Inject constructor() {
        }
        
        // This binding is "scoped".
        // Each request from the same component instance for this binding will
        // get the same instance. Since this is the fragment component, this means
        // each request from the same fragment.
        @FragmentScoped
        class ScopedBinding @Inject constructor() {
        }
```
- <B>Note</B> A common misconception is that all fragment instances will share the same instance of a binding scoped with @FragmentScoped. However, this is not true. Each fragment instance gets a new instance of the fragment component, and thus a new instance of all its scoped bindings.

#### Component default bindings

        Component	                    Default Bindings
        SingletonComponent	                Application2
        ActivityRetainedComponent	        Application
        ViewModelComponent	                SavedStateHandle
        ActivityComponent	                Application, Activity
        FragmentComponent	                Application, Activity, Fragment
        ViewComponent	                    Application, Activity, View
        ViewWithFragmentComponent	        Application, Activity, Fragment, View
        ServiceComponent	                Application, Service



#### Hilt and dagger under the hood

- Hilt uses annotation processors to generate code.
-   Source files ->Compiler ->ByteCodes
                   |
              Annotation Processor
### Hilt Application

- All apps using Hilt must contain an Application class annotated with `@HiltAndroidApp`.
- @HiltAndroidApp kicks off the code generation of the Hilt components and also generates a base class for your application that uses those generated components.


- @AndroidEntryPoint
    - It enable the field injection in Android classes such as
        - `Activity`
        - `Fragment`
        - `Views`
        - `Services`
        - BroadcastReceiver
- ViewModels are supported via a separate API `@HiltViewModel`.

<B>Note: Hilt currently only supports activities that extend ComponentActivity and fragments that extend androidx library Fragment, not the (now deprecated) Fragment in the Android platform.</B>
- Calling setRetainInstance(true) in a Fragment’s onCreate method will keep a fragment instance across configuration changes (instead of destroying and recreating it).
- Hilt fragment can not be retained , because it causes to memory leaks , to prevent this it throw runtime exception if Hilt fragment is retained.
- By default, only SingletonComponent and ActivityComponent bindings can be injected into the view. To enable fragment bindings in your view, add the `@WithFragmentBindings` annotation to your class.
-

### @Module
-
- @InstallIn
    - to indicate which component a module or entrypoint should be installed into.
    - annotation that determines which Hilt component(s) to install the module into
    - Note: If a module does not have an @InstallIn annotation, the module will not be part of the component and may result in compilation errors.
    - A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules.` @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class})` will install a module in both components.
    - We have Rules for Multi-Modules
        - Providers can only be scoped if all of the components support the same scope annotation.
        - Providers can only inject bindings if all of the components have access to those bindings.
        - A child and ancestor component should not install the same module

- @HiltViewModel
    - ```
            @Module
            @InstallIn(ViewModelComponent::class)
            internal object ViewModelMovieModule {
              @Provides
              @ViewModelScoped
              fun provideRepo(handle: SavedStateHandle) =
                  MovieRepository(handle.getString("movie-id"));
            }

            class MovieDetailFetcher @Inject constructor(
              val movieRepo: MovieRepository
            )

            class MoviePosterFetcher @Inject constructor(
              val movieRepo: MovieRepository
            )

            @HiltViewModel
            class MovieViewModel @Inject constructor(
              val detailFetcher: MovieDetailFetcher,
              val posterFetcher: MoviePosterFetcher
            ) : ViewModel {
              init {
                // Both detailFetcher and posterFetcher will contain the same instance of
                // the MovieRepository.
              }
            }
      ```

### Entry point

- An entry point is the boundary where you can get Dagger-provided objects from code that cannot use Dagger to inject its dependencies

#### When do you need an entry point

- You will need an entry point when interfacing with non-Dagger libraries or Android components that are not yet supported in Hilt and need to get access to Dagger objects

#### Create Entry Point

- define an interface with an accessor method for each binding type needed (including its qualifier) and mark the interface with the `@EntryPoint annotation`
- add @InstallIn to specify the component in which to install the entry point

```
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface FooBarInterface {
      @Foo fun getBar(): Bar
    }
```

- Access an EntryPoint

```
    val bar = EntryPoints.get(applicationContext, FooBarInterface::class.java).getBar()

```
- Best Practice

```
   
     @EntryPoint
      @InstallIn(SingletonComponent::class)
      interface MyClassInterface {
        fun getFoo(): Foo

        fun getBar(): Bar
      }

    
        // Call this inside Activity and rest is done.
      fun doSomething(context: Context) {
        val myClassInterface =
            EntryPoints.get(applicationContext, MyClassInterface::class.java)
        val foo = myClassInterface.getFoo()
        val bar = myClassInterface.getBar()
      }
    
```

### Custom component

- there may be situations where the standard Hilt components do not match the object lifetimes or needs of a particular feature.
- Adding a custom component has the following drawbacks:
    - Each component/scope adds cognitive overhead.
    - Custom components work against standardization. The more custom components are used, the harder it is for shared libraries.
    - Components can have only one parent . Reaches in Diamond problem.
    
- Criteria for deciding the use of custom 
    - The component has a well-defined lifetime associated with it. 
    - The concept of the component is well-understood and widely applicable. Hilt components are global to the app so the concepts should be applicable everywhere. Being globally understood also combats some of the issues with cognitive overhead.
    - Consider if a non-Hilt (regular Dagger) component is sufficient. For components with a limited purpose sometimes it is better to use a non-Hilt component. For example, consider a production component that represents a single background task. Hilt components excel in situations where code needs to be contributed from possibly disjoint/modular code. If your component isn’t really meant to be extensible, it may not be a good match for a Hilt custom component.
- Custom component limitations
    - Components must be a direct or indirect child of the SingletonComponent.
    - Components may not be inserted between any of the standard components. For example, a component cannot be added between the ActivityComponent and the FragmentComponent.
    
### Adding a custom Hilt component

- create a class annotated with `@DefineComponent.`  This will be the class used in` @InstallIn` annotations.
    - ```kotlin
                @DefineComponent(parent = SingletonComponent::class)
                interface MyCustomComponent{}
        ```  
- A builder interface must also be defined
- f this builder is missing, the component will not be generated since there will be no way to construct the component.
- This interface will be injectable from the parent component and will be the interface for creating new instances of your component
- Once the component instance generated , now its your job to hold or release.



```kotlin
    @DefineComponent.Builder
    interface MyCustomComponentBuilder {
        fun fooSeedData(@BindsInstance foo: Foo): MyCustomComponentBuilder
        fun build(): MyCustomComponent
    }
```     

```kotlin
    @EntryPoint
    @InstallIn(MyCustomComponent::class)
    interface MyCustomEntryPoint {
        fun getBar(): Bar
    }

    class CustomComponentManager @Inject constructor(
        componentBuilder: MyCustomComponentBuilder) {
    
        fun doSomething(foo: Foo) {
            val component = componentBuilder.fooSeedData(foo).build();
            val bar = EntryPoints.get(component, MyCustomEntryPoint::class.java).getBar()

        }   // Don't forget to hold on to the component instance if you need to!
        }
```

 
      




- Hilt Gradle Plugin
    - Bytecode rewriting
        - Rewriting byte code , it rewrite existing code.
    - class path aggregation
        - Less error
        - Better Performance
        - Better Encapsulation
    

- Q: If we have two same type of object inject into two different activity then how we going to tell them that which one use in which activity.
- And: 
        - Wrong one 
            - create interface 
            - Create two impl1 and impl2 class using interface 
            - Create anothewr class that returning the both impl with same return type as interface
            - Provide @Provides in @Module 
            - As we compile we get the error like ` is bound multiple times:`
        - Now `@Qualifer()` comes in the picture to rescue 
            - It is used to create custom annotations 
            - Create custom annotations.
            - ```
                  public enum class AnnotationRetention {
                  /** Annotation isn't stored in binary output */
                  SOURCE,
                  /** Annotation is stored in binary output, but invisible for reflection */
                  BINARY,
                  /** Annotation is stored in binary output and visible for reflection (default retention) */
                  RUNTIME
            ```
                ```
                    @Qualifier
                    @Retention(AnnotationRetention.BINARY)
                    annotation class Impl1
                    
                    @Qualifier
                    @Retention(AnnotationRetention.BINARY)
                    annotation class Impl2
                ```
            - Now provide @Imp1 and @impl2 in @provides methods to tell module ., which one is going to provide the which one impl.
            - Also tell constractor the thet @impl1 and @impl2.
            - All set

- If you are using Hilt @Inject in any Fragment that is Host by activity A than Activity A must need to use the @AndroidEntryPoint annotations. 
- We can use Fragment constructor injection , using Hilt. 
## Some Testing Tips for Hilt 
    - 
        







