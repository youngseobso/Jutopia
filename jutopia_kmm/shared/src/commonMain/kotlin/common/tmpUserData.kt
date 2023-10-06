package common

object TmpUserInfo {
    private const val memberId: String = "e602882e-30ea-42d5-9fdc-631d2ffb07c1"

    private const val school: String = "ssafy"

    private const val grade: Int = 1

    private const val classNumber = 2

    fun getMemberId(): String {
        return memberId
    }

    fun getSchool(): String {
        return school
    }

    fun getGrade(): Int {
        return grade
    }

    fun getClassNumber(): Int {
        return classNumber
    }

}