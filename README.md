# <p align="center">**Global Converter**</p>



### __Requirements__


- JDK 17+
<br></br>


### Installation

- git clone
```
git clone https://github.com/pelayo-joel/global_converter.git
```

- [JDK](https://www.oracle.com/fr/java/technologies/downloads/)

<br></br>


## <p align="center">**Presentation**</p>
---


<p align="center">Small command line program that converts any String of characters into a specified base (10, 2, 16, 8) based on the ascii value of each characters, also understands binary/hexa/octal/decimal string.</p>
<br></br>

### **Details**
- The structure here is pretty simple:
<br></br>

    - ``Main.java`` is self-explanatory.
    - ``BaseConverter`` is the package that actually converts your string.
    - ``Encryption`` is the package used in BaseConverter to encrypt your string.
<br></br>
- To run the program (while in the bin directory): ``java Main arg0 "arg1" arg2``
<br></br>
    - ``arg0`` reads the desired base (``-b`` for ``binary``, ``-d`` for ``decimal``, ``-h`` for ``hexadecimal``, ``-o`` for ``octal``, ``-t`` for ``text``).
    - ``arg1`` String to convert.
    - (optional) ``arg2`` to encrypt your string. 

    - Notes: 
        - If your string is not text-based, you'll need a white-space at the end to make it understand that you're not converting text, the program should handle the rest.
            - Example: "01001000 01100101" -> text, "01001000 01100101 " -> binary
        - Octal representation shall be noted with a '0' at the beginning (``0110`` instead of ``110``, keeping a convention from C)

<br></br>
### **CREDITS** 

- [Pelayo Joel](https://github.com/pelayo-joel)

