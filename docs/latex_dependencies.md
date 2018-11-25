# Let's make the work with LaTeX document gracefully!

## Installing

You should install a few dependencies to compile `.tex` files and then get `.pdf` file as result:
```
sudo dnf install inotify-tools texlive-xetex texlive-collection-langcyrillic texlive-cm-unicode texlive-lm-math texlive-euenc
```

After that, you should add the function, which located below, to your .bashrc (or .zshrc) file:
```
function texwatch {
  while ! inotifywait -e close_write $1; do
    xelatex $1
  done
}

```

## Using

You can use `texwatch` to easily rebuild your `.tex` to `.pdf` file on the fly, just run:
```
texwatch your_file.tex
```
It works every time when you make changes and save the `.tex` file.

