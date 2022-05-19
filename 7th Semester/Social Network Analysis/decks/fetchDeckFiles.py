#Fetching yugioh deck files
#We will fetch the

#Master duel meta last 4 weeks of tournament (meta decks)
#18-1-2022 until 18-2-2022
#Release date of the game was on 18-1-2022

#URL for fetching
#https://www.masterduelmeta.com/top-decks#dateRange=Last%204%20weeks&tournamentsOnly&page=1


from bs4 import BeautifulSoup
import requests
import json
import time
import os
from selenium import webdriver

##main_page = "https://www.masterduelmeta.com"
##base_url = "https://www.masterduelmeta.com/top-decks#dateRange=Last%204%20weeks&tournamentsOnly&page="
##page = 1

driver = webdriver.Chrome()
main_page = "https://ygoprodeck.com/"
base_url = "https://ygoprodeck.com/deck-search/?&_sf_s=master%20duel&sort_order=views&post_date=2022-01-18%202022-02-18&offset="
offset = 0
def getDeckLinksFromPage(url, page):
    #Gets into the html page and returns a list of urls of decks

    #Make the GET request and get the HTML content of the page
    url = '{}{}'.format(base_url, page)
    attempts = 0
    wait_time_seconds = 5
    
    for attempt in range(0,10):
        try:
            # Make a GET request to fetch the raw HTML content

            driver.get(url)



##            html_content = requests.get(url).text
        except:
            print("Error on page with number: ", page)
            print("Pausing for {} seconds.".format(wait_time_seconds))
            time.sleep(wait_time_seconds)
            attempts += 1
            continue
        break
    if attempts > 9:
        return False

    links = driver.find_element_by_css_selector('a')
    
   #Parse the html content
##    soup = BeautifulSoup(html_content, "html.parser")
##    links = []
##    for link in soup.findAll('a'):
##        print("hi")
##    for link in soup.findAll('a[href*="{}"]'.format(main_page)):


##    <a href="/top-decks/anytime/2/adamancipator/buns/" class="svelte-1kel21"><div class="img-container svelte-1kel21"><div class="card-rarity-container svelte-en6kzk border"> <div class="card svelte-en6kzk" style=""><div class="svelte-1fmly4d" style="--ratio:1;"> <a class="image-wrapper svelte-en6kzk">  <img class="card-img full-width svelte-en6kzk" alt="Adamancipator" srcset="https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=50 50w, https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=100 100w, https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=140 140w, https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=200 200w, https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=260 260w, https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=360 360w, https://imgserv.duellinksmeta.com/v2/mdm/deck-type/Adamancipator?portrait=true&amp;width=420 420w" sizes="(min-width: 576px) 104px, (max-width: 575px) 20vw"></a></div></div></div></div></a>

##    soup.select('.svelte-1kel21[href]')
##    for link in soup.select('.svelte-1kel21'):
##        print("h")
##    
##    for link in soup.findAll('a[href]'):
##        print("hi")
##    for link in soup.findAll('a[href*="/top-decks/"]'):
##        links.append(main_page + str(link[href]))
##        print(link)
##        url = str(link.get('href'))
##        if url.startswith("/top-decks/"):
##            links.append(main_page + url)

##    for token in str(html_content).split():
##        if str(token).startswith("href=\"https://ygoprodeck.com/"):
##            print(token)

    print(links)
    return links

getDeckLinksFromPage(base_url, offset*10)

    
    
    
