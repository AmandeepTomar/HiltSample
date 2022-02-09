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
    class MyClass : NonHiltLibraryClass() {

      @EntryPoint
      @InstallIn(SingletonComponent::class)
      interface MyClassInterface {
        fun getFoo(): Foo

        fun getBar(): Bar
      }

      fun doSomething(context: Context) {
        val myClassInterface =
            EntryPoints.get(applicationContext, MyClassInterface::class.java)
        val foo = myClassInterface.getFoo()
        val bar = myClassInterface.getBar()
      }
    }
```


- Hilt Gradle Plugin
    - Bytecode rewriting
        - Rewriting byte code , it rewrite existing code.
    - class path aggregation
        - Less error
        - Better Performance
        - Better Encapsulation






