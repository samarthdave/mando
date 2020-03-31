<h1 align="center">
	Mando
	<br />
	<img
		width="150"
		alt="Mandalorian"
		src="/mando.png">
</h1>

<blockquote>
  Hey Mando! You spilled my drink...
</blockquote>

<hr />

## Installation
Requires Python >=3.7

```
pip install mandalorian
```

## Usage

Goals for this  project:

```
# default usage
mando HalfAdder.hdl
# -----


# supply a directory to test all hdl files within
mando ./chips/01

# listens for changes to all local HDL files
# and runs respective 
mando ./*.hdl

# recursively test all dependent chips
# (depends on Xor & And gates)
mando -r HalfAdder.hdl

# Show syntax errors
mando -s Add4.hdl
# > outputs: Comparison failure on line ...
```



#### Resources
- [Mandalorian Emoji](https://www.starwars.com/news/the-mandalorian-and-the-child-coming-to-disney-emoji-blitz)
