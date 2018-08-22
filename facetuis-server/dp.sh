#!/bin/sh
app_path = /www/app/facetuis
projectName = facetuis

# 拉取最新代码
a = $(git pull  2>&1)
echo $a

b = $(gradle clean build -x test 2>&1)
echo $b

