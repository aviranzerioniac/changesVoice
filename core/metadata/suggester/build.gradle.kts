plugins {
  id("voice.library")
  alias(libs.plugins.metro)
}

dependencies {
  implementation(projects.core.common)
  implementation(projects.core.data.api)
  implementation(projects.core.documentfile)
  implementation(libs.metro.runtime)
  implementation(libs.datastore)

  testImplementation(libs.bundles.testing.jvm)
}
