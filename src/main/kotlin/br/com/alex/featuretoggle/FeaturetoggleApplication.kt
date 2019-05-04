package br.com.alex.featuretoggle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FeaturetoggleApplication

fun main(args: Array<String>) {
	runApplication<FeaturetoggleApplication>(*args)
}
