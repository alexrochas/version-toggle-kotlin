package br.com.alex.featuretoggle

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class Api(
) {

   @GetMapping("/cool")
   @VersionToggle("#{api.minimumVersion}")
   fun get(): ResponseEntity<String> {
      return ResponseEntity.ok("Cool")
   }

   @ExceptionHandler(UnsupportedVersionException::class)
   fun illegalVersion(exception: UnsupportedVersionException): ResponseEntity<String> {
      return ResponseEntity.badRequest().body(exception.message)
   }

}