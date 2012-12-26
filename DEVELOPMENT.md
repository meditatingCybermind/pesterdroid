Development
===========

Getting the Code
----------------
<pre>
git clone https://github.com/kiooeht/pesterdroid.git
git submodule init
git submodule update
</pre>
Note that submodule is required to pull in the ActionBarSherlock library.

### Eclipse
To set up your Eclipse environment for the first time for Pesterdroid:

1. File > Import > Android > Existing Androd Code Into Workspace
2. Root Directory > Browse...
3. Select the directory Pesterdroid is located in
4. Under _Projects_ only select **Pesterdroid**, **ActionBarSherlock**, and **Android-CollapsibleSearchMenu**
5. Finish
6. Wait a few moments as the workspace gets built, if you get some errors in the console about R.java being modified manually, you can ignore them.

Code Style
----------
Mainly for the coding style use common sense:

* Make it understandable
* Comment your code
* No trailing whitespace

The main caveat:
### Indentation
Use tabs for indentation and spaces for alignment.
