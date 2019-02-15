package com.thecubecast.ReEngine2.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.PrintWriter
import java.util.*

/** Created by Darius on 15-Feb-19. */
class DcpTexturePackerManager {
    var exportAtlasFolder = "textureAtlas"
    var imagesOrigin = "../../images/atlas"
    var hashFileName = "textureHash.txt"

    /** Will use texturePacker to pack all images, only if files have changed or atlas output files are not created
     * Loads settings from pack.json files in folders
     * Gradle task texturePacker does the same packing, Android launcher won't pack so use the task if needed
     *
     * Images starting with _ are deleted automatically */
    fun checkWhetherToPackImages() {
        println("_____\nChecking image hashes...")
        var initTime = System.currentTimeMillis()

        val hashFile = File(imagesOrigin + "/" + hashFileName)

        fun hashAllFiles(file: File, hashFile: File): Int {
            var total = 0
            if (file.isDirectory) {
                for (content in file.listFiles()) {
                    total += hashAllFiles(content, hashFile)
                }
            } else {
                if (file == hashFile) {
                    return 0
                }

                val input = FileInputStream(file.path)
                var c = 0
                while (c != -1) {
                    c = input.read()
                    total += c
                }
                input.close()
            }
            return total
        }

        var hashingTotal = hashAllFiles(File(imagesOrigin), hashFile)
        var c = 0
        try {
            val atlasFile = File(exportAtlasFolder + "/atlas.atlas")
            val atlasImageFile = File(exportAtlasFolder + "/atlas.png")
            if (!hashFile.exists() || !atlasFile.exists() || !atlasImageFile.exists()) {
                val pw = PrintWriter(hashFile)
                pw.print(-1)
                pw.close()
            }
            val s = Scanner(hashFile)
            c = s.nextInt()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        println("Finished; time elapsed: " + java.lang.Double.toString((System.currentTimeMillis() - initTime) / 1000.toDouble()) + " s\n_____\n")

        if (hashingTotal != c) {
            initTime = System.currentTimeMillis()
            println("_____\nPacking images...")

            var hadToDelete = false
            // delete images starting with _
            fun invalidFile(file: File) = (file.name.endsWith(".png") && file.name.startsWith("_"))

            for (file in File(imagesOrigin).walk()) {
                if (file.isFile) {
                    if (invalidFile(file)) {
                        println("Deleting ${file.name}")
                        file.delete()
                        hadToDelete = true
                    }
                }
            }

            println()

            TexturePacker.process(imagesOrigin, exportAtlasFolder, "atlas")

            if (hadToDelete) hashingTotal = hashAllFiles(File(imagesOrigin), hashFile)

            // save new hash value
            val pw = PrintWriter(hashFile)
            pw.print(hashingTotal)
            pw.close()

            println("Finished; time elapsed: " + java.lang.Double.toString((System.currentTimeMillis() - initTime) / 1000.toDouble()) + " s\n_____\n")
        }
    }
}