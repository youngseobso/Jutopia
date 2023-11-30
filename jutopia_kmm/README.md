# Kotlin Multiplatform Mobile

## Kotlin Multiplatform

- kotlin/native로 멀티플랫폼 프로그램을 작성하는 개발방식
- 비즈니스 로직을 kotlin으로 작성한다
- 비즈니스 로직에서 해결하지 못하는 부분은 Native로 작성한다

## 프레임워크

- compose multiplatform
  - `kotlin multiplatform` 에서 사용 가능한 `jetpack compose`
  - JetBrain 에서 개발
  - [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
  - [Compose Multiplatform Wizard](https://terrakok.github.io/Compose-Multiplatform-Wizard/)
  - +지원 플랫폼

|iOS|Android|Desktop|Web|
|:---:|:---:|:---:|:---:|
|`Alpha`|Jetpack Compose|Windows, MacOS, Linux|`Experimental`|

> 현재 **네비게이션 기능**은 Android 플랫폼에서만 지원한다 ([링크](https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Navigation))


## 라이브러리

- kmp awesome
  - kmm 호환 라이브러리 모음 깃 허브
  - [kmp awesome](https://github.com/terrakok/kmp-awesome#-compose-ui)

|라이브러리|설명|DOCS|사용버전|
|:---:|:---|:---:|:---:|
|[PreCompose](https://github.com/Tlaster/PreCompose)|**네비게이션** 기능<br/>**viewModel** 기능|[github](https://tlaster.github.io/PreCompose/)|1.5.3|
|[kermit](https://github.com/touchlab/Kermit)|로그 기능</br>iOS의 **NSLog**, Android의 **Logcat** 지원|[kermit](https://kermit.touchlab.co/)|2.0.0|
|[AAY-Chart](https://github.com/TheChance101/AAY-chart)|Line, Bar, Pie, Radar chart|[github](https://github.com/TheChance101/AAY-chart)|Beta-0.0.5|
|[Ktor](https://github.com/ktorio/ktor)|비동기 HTTP 통신|[Ktor.io](https://ktor.io/)|2.3.4|
|[KStore](https://github.com/xxfast/KStore)|파일기반 객체 저장|[github](https://xxfast.github.io/KStore/)|0.6.0|

## 참고 사이트

- Font
  - [Common 에서 사용가능한 FontFamily 제작](https://jassielcastro.medium.com/custom-fonts-in-android-and-ios-applications-using-kotlin-multiplatform-and-jetpack-compose-c88d2d519e77)