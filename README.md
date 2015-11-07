# RecyclerNotifier

Inform your app's users of update in data presented in `RecyclerView`s.

You can attach the notifier anchored on top (default) or bottom of your `RecyclerView` (or both top and bottom).
The API also allows you to set various properties of the notifier, including `drawable`, `visibility` of image and text,
`textColor` and so on.

The text will be limited to one `singleLine` and will add `ellipsis` (`...`) to the end of the text if needed.

<img src="https://raw.githubusercontent.com/julioz/recyclernotifier/master/img/library_screen_top.png" height="450" />
<img src="https://raw.githubusercontent.com/julioz/recyclernotifier/master/img/library_screen_bottom.png" height="450" />

- - - 
Usage
--------
Initialize a `RecyclerNotifier` like so:
```java
RecyclerNotifier recyclerNotifier = new RecyclerNotifier(getContext());
```
Then attach it to your `RecyclerView` using:
```java
// supposing mRecyclerView is the instance of your Recycler View:
RecyclerNotifierAttacher.attach(recyclerNotifier, mRecyclerView);
```

After that, you're all set! Just call `show()` and `hide()` whenever you want to change the `RecyclerNotifier` visibility.
The library brings a smooth animation built-in.

#### Attributes
You can set a regular `OnClickListener` on it through `setOnClickListener(...)`,
and all the other properties through your instance of `RecyclerNotifier`.

#### Anchoring
If needed, you can set the anchor of your `RecyclerNotifier` to `TOP` or `BOTTOM` by using the following call:
```java
RecyclerNotifierAttacher.attach(recyclerNotifier, mRecyclerView, RecyclerNotifierAttacher.ANCHOR_BOTTOM);
```
You can even have two `RecyclerNotifier`s, one attached to `TOP` and another to `BOTTOM`!

#### Scroll listener
There is also a built-in `OnScrollListener`: the `RecyclerNotifier` will hide automatically if you scroll
your `RecyclerView` downwards and reapper if you scroll it up. To use it, you must simply call:
```java
mRecyclerView.addOnScrollListener(mRecyclerNotifier.getOnScrollListener());
```

Suppose you have a background thread fetching new data for your `RecyclerView`, and you want to show the
`RecyclerNotifier` after the new data is fetched. When the user sees the data, the `RecyclerNotifier` will be hidden
and won't show up until another fetch is started.
To do that, you can simply set the `Notifier`'s `ScrollListener` to your `RecyclerView`, and after your fetch is done you
must call:
```java
mRecyclerNotifier.show();
```
When your user sees the new data, simply set the `Notifier` to ignore new scroll events by using
```java
mRecyclerNotifier.setChangeVisibilityWithScrollListener(false);
```
As soon as you have new data you can reset that flag to `true` in order for the
`Notifier` to be shown as soon as the user scrolls.

- - - 
Download
--------

Right now the library is not yet available through gradle, but this is in the roadmap. For now,
just checkout the directory folder and add it as a module to your project, or download the *aar* from the
repository adding it to the `libs` directory in your project.

Then, add this to your `build.gradle` dependencies:

```groovy
compile 'br.com.zynger.recyclernotifier:recyclernotifier:1.0@aar'
```

And this to your `repositories` node in the top-level `build.gradle`:
```groovy
allprojects {
    repositories {
        jcenter()
        flatDir {
          dirs 'libs'
        }
    }
}
```



License
-------

    Copyright 2015 Julio Zynger, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
