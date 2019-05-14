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
In the [`Manifest`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/AndroidManifest.xml) add only permission on the Internet. Read the comments in the code

## gradle
In the [`gradle`](https://github.com/GssGuru/Main-Beginner/blob/master/app/build.gradle) add only dependencies on the Internet and for images from internet. Read the comments in the code

## Aplication code

[`Aplication code`](https://github.com/GssGuru/Main-Beginner/tree/master/app/src/main/java/guru/gss/mainbeginner) - is the code with the mechanics of the application.
Carefully read the code comments.

Since this project is for beginners, we will write everything in activity and Fragment. Without using any architectural solutions.

In the [`MainActivity`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/MainActivity.java) we add a navigation menu to choose which news feed to display. News feed reflecting using an [`AdapterNewsFeed`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/AdapterNewsFeed.java) and located in the [`FragmentNewsFeed`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/FragmentNewsFeed.java). We make a request to the server to receive the news feed in the [`FragmentNewsFeed`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/FragmentNewsFeed.java). If an error occurs in the request, then show a [`DialigError`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/DialigError.java) with an error and the ability to either repeat the request or exit from the application.

В конце у нас должно получитса
- [`MainActivity`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/MainActivity.java)
- [`FragmentNewsFeed`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/FragmentNewsFeed.java)
- [`AdapterNewsFeed`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/AdapterNewsFeed.java)
- [`DialigError`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/DialigError.java)
- [`ModelNewsFeed`](https://github.com/GssGuru/Main-Beginner/blob/master/app/src/main/java/guru/gss/mainbeginner/ModelNewsFeed.java)

## Resources code
[`Res folder.`](https://github.com/GssGuru/Main-Beginner/tree/master/app/src/main/res) Change only Application Name

</p>
</details>
