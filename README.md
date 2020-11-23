## Welcome to GitHub Pages

You can use the [editor on GitHub](https://github.com/finalaurora/multisvc-sys/edit/main/README.md) to maintain and preview the content for your website in Markdown files.

Whenever you commit to this repository, GitHub Pages will run [Jekyll](https://jekyllrb.com/) to rebuild the pages in your site, from the content in your Markdown files.

### Markdown

Markdown is a lightweight and easy-to-use syntax for styling your writing. It includes conventions for

```markdown
Syntax highlighted code block

# Header 1
## Header 2
### Header 3

- Bulleted
- List

1. Numbered
2. List

**Bold** and _Italic_ and `Code` text

[Link](url) and ![Image](src)
```

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/finalaurora/multisvc-sys/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.

## General flow

General flow of the modules

```plantuml
@startuml
actor user
participant frontend as "Front-End"
participant jetty as "Jetty Server"
participant tserver as "Thrift Server"
database db as "Mongo DB"
user -> frontend: Perform action
frontend -> jetty: Send request by using API through XHR, REST
jetty -> tserver: Service communication through Thrift framework
tserver -> db: JPA access through Mongo driver
db --> tserver: send back query data
tserver --> jetty: Return objects through thrift communication
jetty --> frontend: Serialize data and return JSON for front-end processing
frontend --> user: Visualize data as web page
@enduml
```