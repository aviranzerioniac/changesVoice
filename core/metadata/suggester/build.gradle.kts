plugins {
  id("voice.library")
  alias(libs.plugins.metro)
  alias(libs.plugins.kotlin.serialization)
}

dependencies {
  implementation(projects.core.common)
  implementation(projects.core.data.api)
  implementation(projects.core.documentfile)
  implementation(projects.core.data.impl)
  implementation(libs.metro.runtime)
  implementation(libs.datastore)

  implementation("javax.inject:javax.inject:1")

  implementation(libs.serialization.json)

  testImplementation(libs.bundles.testing.jvm)
}
