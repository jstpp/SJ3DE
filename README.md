# SJ3DE
My first attempt using Java for 3D data visualization. **Goal is to build 3D visualization tool for LiDAR measurements.**
<hr>
<p align="center">
    <img src="https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge" alt="CodeFactor" />
    <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/jstpp/SJ3DE?style=for-the-badge">
    <a href="https://www.codefactor.io/repository/github/jstpp/SJ3DE"><img src="https://www.codefactor.io/repository/github/jstpp/SJ3DE/badge?style=for-the-badge" alt="CodeFactor" /></a>
</p>

## Usage
#### How can I navigate?
Navigation: \
`Q` go up \
`E` go down \
`W/A/S/D` classical navigation \
_To rotate camera, use right mouse button. To use tool from toolbox, use left button._

Options: \
`F1` debugging \
`F2` toolbox
<br />

#### How can I modify observed objects?
To modify/add/remove new objects (including importing .LAZ files) go to the `Structures` tab.
Moreover, you can add new simple `Space` structures defined by mathematical expressions using 
text panel on the bottom of the `View` tab.

## Screenshots
Moszna Palace (46.5 MB .laz file)
![Screenshot 1](screenshots/S1.png)

Mathematical expression
![Screenshot 2](screenshots/S2.png)

## Credits
SJ3DE uses external libraries:
* `exp4j` by [objecthunter](https://www.objecthunter.net/exp4j/team-list.html),
* `laszip4j` by [Marcel Reutegger](https://github.com/mreutegg).

