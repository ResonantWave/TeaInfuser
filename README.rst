==========
TeaInfuser
==========
Raspberry Pi based automatic tea infuser, controlled with an android app.

Installation and usage
======================

Just drop the `tea.py`_ into your Raspberry Pi and run it with ``python tea.py``. Also, attach a servo to BCM pin 18. Once started, the script will start listening on port 8000. You can change this on line 57 of `tea.py`_.


Android app
===========

Import the `TeaPreparer`_ folder into Android Studio. Change the address on `Main.java`_ (line 64), so it points to your Raspberry Pi. That's it!


Contributors
============

Development:

* `NiXijav`

Contributing
============

* The code is licensed under the `GNU GPL V3`_
* The project source code is hosted on `GitHub`_
* Please use `GitHub issues`_ to submit bugs and report issues
* Feel free to contribute to the code

.. _tea.py: tea.py
.. _TeaPreparer: TeaPreparer
.. _Main.java: TeaPreparer/app/src/main/java/com/ionicbyte/teapreparer/Main.java
.. _GNU GPL V3: LICENSE
.. _GitHub: https://github.com/ResonantWave/TeaInfuser
.. _GitHub Issues: https://github.com/ResonantWave/TeaInfuser/issues
