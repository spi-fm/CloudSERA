![CLOUDsERA.jpeg](https://github.com/spi-fm/CloudSERA/blob/master/images/CloudSERA-sm.jpeg)

# Cloud Systematic Evidence-based Review of Articles
Tool for supporting systematic reviews, both systematic literature reviews and systematic mappings.

This tool is also described in the paper *A Comprehensive Framework to Reinforce Evidence Synthesis Features in Cloud-Based Systematic Review Tools* available at: https://www.mdpi.com/2076-3417/11/12/5527


## Quick start
~~Demo online at [CloudSERA](http://slr.uca.es)~~

## Software License
This project licence is Eclipse Public License [(EPL-1.0).](https://www.eclipse.org/legal/epl-v10.html)

## 1. User guide
The instructions for using the CloudSERA tool are detailed below.
### 1.1 Introduction
The **CloudSERA** tool is a web application consisting of free software, which can be executed in the context presented. In addition, the user can adapt it to their needs.

Before starting the user should remember that it is **necessary to have a Mendeley account** so that the web application can store all bibliographic resources. If you do not have an account, you can register through the following link [Mendeley](https://www.mendeley.com/newsfeed/.).

### 1.2 Instalation
No installation is necessary by the user. The management of the bibliographic references can be done online through the Mendeley website. However, the user can install the Mendely desktop application to perform the maintenance of the references. The desktop program can be downloaded from the [Mendeley website](https://www.mendeley.com/download-mendeley-desktop?_section=footer&switchedFrom=).

To begin the guide of this project, we proceed to enter the following link: [http://slr.uca.es/](http://slr.uca.es/).

### 1.3 Register User in application
New users must register on the main **CloudSERA** website, by clicking the [**Register now button**](http://slr.uca.es/register), with their Mendely user (email and password).

![User registration in application](https://github.com/spi-fm/CloudSERA/blob/master/images/00Register1.png)

If you do not remember your information you must access the Mendeley page to retrieve your username and password. Once you have entered your information, a welcome message will appear and you can access the tool's operations.

![User registration in application](https://github.com/spi-fm/CloudSERA/blob/master/images/01Welcome.png)

### 1.4 Enter in the application
In case you already have the registration in the application, choose the option [**Sing up today**](http://slr.uca.es/login/auth?format=) and enter the user's email and password of Mendeley.


![Login](https://github.com/spi-fm/CloudSERA/blob/master/images/02Login.png)

### 1.5 User Profile
One that the user has entered into the **CloudSERA** tool will appear a menu where the user can change their profile. The user profile collects the most important data from your Mendeley account, if these were not updated the user can press the synchronize button (Mendeley) to update this information.

![Profile](https://github.com/spi-fm/CloudSERA/blob/master/images/04Profile1.png)

### 1.6 Systematic Literature Review
Next we will explain the procedure for the creation of a systematic review of the literature. This process, we will divide it into four steps

1. Define Systematic Review of Literature
2. Research questions
3. Criteria for classifying references
4. Specific attributes of the references

These steps can be executed individually or through a wirazd that will guide us through each of them.

![CreateSLR](https://github.com/spi-fm/CloudSERA/blob/master/images/05CreateSLR.png)


#### 1.6.1 Define Systematic Literature Review
In this option, the title and the justification of the revision are requested.

![NameSLR](https://github.com/spi-fm/CloudSERA/blob/master/images/06SLRName.png)

Once these fields are completed, the ** CloudSERA ** will connect with Mendeley and create a folder with the name of that revision and several subfolders, one for each reference search engine that ** CloudSERA ** can use.

![NameSLRMendeley](https://github.com/spi-fm/CloudSERA/blob/master/images/06SLRNameMendeley.png)

#### 1.6.2 Create research questions
A systematic review of the literature should answer a series of research questions that may be introduced in the application. To do this, choose the option "Create research question" and fill in the options. New questions can be created, modified or eliminated.

![NameSLR](https://github.com/spi-fm/CloudSERA/blob/master/images/07Questions.png)

#### 1.6.3 Create criteria for the classification of references
The bibliographic references consulted should be classified following an inclusion / exclusion criterion. A systematic review, by default, will have a classification criterion called included that indicates that all references will be included as part of the study.

To insert new criteria, we must choose the option of creating criteria.
criterion

![NameSLR](https://github.com/spi-fm/CloudSERA/blob/master/images/08Critera.png)


#### 1.6.4 Create specific attributes of the references
As with the criteria, a bibliographic reference may have attributes created by the user that are not provided by Mendeley. These specific attributes can be from
textual, numerical type or be part of a list.

![NameSLR](https://github.com/spi-fm/CloudSERA/blob/master/images/09Attributes.png)

### 1.7 Create reference searches
For the creation of searches of bibliographic references we must choose the option 'Searches' whose systematic review you want to perform the search. **CloudSERA** the  will show us on the screen all the searches made for this review.

![NameSLR](https://github.com/spi-fm/CloudSERA/blob/master/images/10Search.png)

We can create a new search by entering the following parameters:
* **Search Engine**. In the creation of this application we will have the following
Search engine references: ACM Library, IEEE Xplore Digital Library, Science Direct, Springer Link.
* **Total maximum**. Number of maximum bibliographic references to be found by each of the assigned search engines.
* **Years**. Range of years in which the reference to search must be found.
* **Terms**. The search terms that should appear in the references. We can insert as many as the user wants. The terms are composed of:
    * **Search component**. Indicates in which component of the reference you should find the indicated term: Abstract, Any Field, Author, Review, Title, etc.
    * **Search operator**. Indicates what type of operator should be applied for the
terms that are inserted, if you must include ALL, ANY or NONE.
    * **Terms**. The terms to be found in each of the references
    
Once we have clicked on the button 'Create search', a search will be carried out in the background with the information provided while we can do another operation within the application.

![Parameters](https://github.com/spi-fm/CloudSERA/blob/master/images/11Options.png)
