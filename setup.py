import setuptools

with open("README.md", "r") as fh:
    long_description = fh.read()

setuptools.setup(
    name="mandalorian",
    version="0.0.2",
    author="Cameron Brill",
    author_email="contact@cameronbrill.me",
    description="nand2tetris CLI helper utils",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/samarthdave/mando",
    packages=setuptools.find_packages(),
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.7',
)
