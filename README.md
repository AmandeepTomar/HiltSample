# HiltSample
Hilt Dependency Injection

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
          implementation "com.google.dagger:hilt-android:2.38.1"
          kapt "com.google.dagger:hilt-compiler:2.38.1"
      }
```

