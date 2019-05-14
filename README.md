# Main Page
Simple main page with news feeds using fragments, dialogs, adapter, navigation menu

# Attention! 
This example was created exclusively for very beginner programmers. For such a code you will be dismissed from work but at this stage he will help you understand the essence of the work.

# Preview
![](http://media.giphy.com/media/fHlMhMIIByBLImbAIv/giphy.gif) ![](http://media.giphy.com/media/1ipjUVgMqKEuWs6TuM/giphy.gif)

# Code
Description of the application code
<details><summary>Open</summary>
<p>

## Manifest
In the [`Manifest`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/AndroidManifest.xml) - is the code with the mechanics of the application. Carefully read the code comment

## gradle
In the [`gradle`](https://github.com/GssGuru/Main-Beginner/blob/master/app/build.gradle) add only dependencies on the Internet and for images from internet. Read the comments in the code

## Aplication code

[`Aplication code`](https://github.com/GssGuru/Login-Beginner/tree/master/app/src/main/java/guru/gss/loginbeginner) - is the code with the mechanics of the application.
Carefully read the code comments.

Since this project is for beginners, we will write everything in activity and Fragment. Without using any architectural solutions.

В приложении понадобитса Навигационное меню для Для вибора кокую именно новосную ленту отобразить. Лента новостей отображающея с помощю  адаптера будет находитса фрагменте где и будет происходить сам визов. Также в фрагменте ми будем обробативать сам визов и если у нас будет происходть ошибка в звпросе то ми будем показивать дивлоговое окно с ошибкой и возможностю либо повторить запрос, либо вийти из приложения.

В конце у нас должно получитса
- MainActivity
- FrafmentNewsFeed
- AdapterNews
- ErrorDialog

## Resources code
[`Res folder.`](https://github.com/GssGuru/Main-Beginner/tree/master/app/src/main/res) Change only Application Name

</p>
</details>
