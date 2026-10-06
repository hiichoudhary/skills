plugins {
  alias(libs.plugins.android.library)
  `maven-publish`
}

android {
  namespace = "com.example.skills"
  compileSdk {
    version = release(37)
  }

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.keep")
  }

  buildTypes {
    release { }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  publishing {
    singleVariant("release") {
      withSourcesJar()
      withJavadocJar()
    }
  }
}

dependencies {
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.ktx)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
}

afterEvaluate {
  publishing {
    publications {
      register<MavenPublication>("release") {
        from(components["release"])
        groupId = "com.example"
        artifactId = "skills"
        version = "1.0.0"
      }
    }
    repositories {
      maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/hiichoudhary/skills")
        credentials {
          username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
          password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
      }
    }
  }
}
