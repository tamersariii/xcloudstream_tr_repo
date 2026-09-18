rootProject.name = "xcloudstream_tr_repo"

// Kök dizindeki tüm alt klasörleri otomatik olarak modül olarak dahil et.
// 720izle klasörü silindiği için otomatik olarak listeden çıkar.
File(rootDir, ".").eachDir { dir ->
    if (File(dir, "build.gradle.kts").exists()) {
        include(dir.name)
    }
}

fun File.eachDir(block: (File) -> Unit) {
    listFiles()?.filter { it.isDirectory }?.forEach { block(it) }
}
