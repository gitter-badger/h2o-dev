setwd(normalizePath(dirname(R.utils::commandArgs(asValues=TRUE)$"f")))
source('../h2o-runit.R')

test.rdoc_cut.golden <- function(H2Oserver) {

irisPath <- system.file("extdata", "iris_wheader.csv", package="h2o")
iris.hex <- h2o.uploadFile(H2Oserver, path = irisPath, destination_frame = "iris.hex")
summary(iris.hex)
sepal_len.cut <- cut.H2OFrame(iris.hex$sepal_len, c(4.2, 4.8, 5.8, 6, 8))
head(sepal_len.cut)
summary(sepal_len.cut)

testEnd()
}

doTest("R Doc Cut Status", test.rdoc_cut.golden)

