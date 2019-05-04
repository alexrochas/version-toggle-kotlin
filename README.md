# version-toggle-kotlin
> Minor feature toggle implemented in kotlin with AOP

## Usage

The first idea was to fetch the User-Agente header and use a toggle based on the version:

```kotlin
   @GetMapping("/cool")
   @VersionToggle("#{api.minimumVersion}")
   fun get(): ResponseEntity<String> {
      return ResponseEntity.ok("Cool")
   }
```

Given the **application.properties**:

```properties
api.minimumVersion=11.0.0
```

The current response for this code could be `Cool` or `Unsupported agent version.`.

## Meta

Alex Rocha - [about.me](http://about.me/alex.rochas)
