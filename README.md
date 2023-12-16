# Dr Stone Mod
In this mod I have aimed to make a recreation of various items and tools from the popular anime/manga dr stone written by Riichiro Inagaki and illustrated by the South Korean artist Boichi and animated by TMS entertainment.
This mod is fan made and not affiliated with the official creators.
this is still a work in progress mod.

# So far:
Petrification device Item: 

  this item can be thrown or kept inventory
  takes input from chat in the format of "meters,seconds" replace the words with the wanted values.
  once the countdown is reached, it will create a petrification sphere of the desired size.

Petrification sphere:

  this is an expanding sphere of green light that petrifies entities within it and touching it.

Revival fluid:

  this can revive petrfied entities, just throw it at them.

Guano:

  bat poop. right now it doesnt spawn naturaally. soon will spawn in caves

# known bugs:
- countdown in on petrification device in the inventory is not specific to an item and aplied inventory wide, if the petrification item(s) is removed from invetory the countdown pauses and resumed when another one enters the inventory

  I do plan to address these issues soon.

# future plans:
  - fix current bugs
  - do some optimization, cleanup, documentation, etc.
  - add golden spear
  - make petrification device spawn in certain structures where you can find it.
  - add a way to get nitric acid and alcohol for crafting revival fluid.
  - add platinum ore (part of getting nitric acid later on).
  - add science stuff.
  - make guano spawn naturally in cave.
  - make katana.

# Credits
I would like to acknowledge the contributions of Raptorfarian and Alexthe666, the creators of the Ice and Fire mod. Their work has influenced the development of this project. Specifically, 
I have used and modified substantial portions of code from their 'stone statue' and 'stone statue renderer' classes under the LGPL-3.0 license. This code is implemented in my 'petrified entity' class and 'petrified entity renderer' within this Dr. Stone themed mod.
These acknowledgments are not only present here in this README but are also clearly indicated through comments within the respective classes where their code has been utilized and adapted.
here are links to the classes are borrowed from:
https://github.com/AlexModGuy/Ice_and_Fire/blob/1.18.2/src/main/java/com/github/alexthe666/iceandfire/client/render/entity/RenderStoneStatue.java
https://github.com/AlexModGuy/Ice_and_Fire/blob/1.18.2/src/main/java/com/github/alexthe666/iceandfire/entity/EntityStoneStatue.java
