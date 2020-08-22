import requests as rq
from bs4 import BeautifulSoup 
import urllib.request

#import validators as val
import re

urls = []
sub_urls = []
f = open("D:/study/Advanced Computing concepts/urls_medium.txt", "a")
num = 1
def trade_spider(urls,max_page,num):
    page = 1
    count = 50;
    url = 'https://www.quora.com/topic/Machine-Learning'

    while page<=max_page:
         source_code = rq.get(url)
        
         plain_text = source_code.text
         #print(plain_text)
         regex = re.compile(
         r'^(?:http|ftp)s?://' # http:// or https://
         r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)|' #domain...
         r'localhost|' #localhost...
         r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})' # ...or ip
         r'(?::\d+)?' # optional port
         r'(?:/?|[/?]\S+)$', re.IGNORECASE)
         #print("\t\tpage",page)
         print("printed page")
         soup = BeautifulSoup(plain_text,features="lxml")
         #print("1")
         
         for link in soup.findAll('a'):
                #print(soup.findAll('a'))
                #if count == 0:
                    #break

                href = link.get('href')
                #print(link)
                #print("2")
                count -= 1
                try:
                    if href.find('/') == 0:
                        href = href[1:]
                        href = url+href
                except:
                    print("\t\t\t find error for ",href)
                if re.match(regex,str(href)):
                    #print("3")
                    print("=>",href)
                    if href not in urls:
                            #print("##########################################################")
                            urls.append(href)
                            #s = str(href)
                            #if "http://"  in href:
                            #    s = s.replace("http://","")
                            #    s = s.replace("/","_")
                            #if "https://"  in href:
                            #    s = s.replace("http://","")
                            #    s = s.replace("/","_")
                            #    print(s)
                            #urllib.request.urlretrieve(href,'D:/study/Advanced Computing concepts/url_pages/'+str(s)+'.html')#,'./'+link[link.find('/turnstile_')+1:] 
                            #print("length:",len(urls))
                            #f.write(href+"\n")
                            #print("=>",num," ",href)
                            num+= 1
                            get_single_links(urls,href,num)
                            #count = count + 1 
                            #print(count)

                    page = page+1
        
def get_single_links(urls,url,num):

        
     source_code = rq.get(url)
     #print(source_code)
     regex = re.compile(
         r'^(?:http|ftp)s?://' # http:// or https://
         r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)|' #domain...
         r'localhost|' #localhost...
         r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})' # ...or ip
         r'(?::\d+)?' # optional port
         r'(?:/?|[/?]\S+)$', re.IGNORECASE)
     plain_text = source_code.text
     soup = BeautifulSoup(plain_text)
     for link in soup.findAll('a'):
             href = link.get('href')
             #print("\t\t\t\t",href)
             if re.match(regex,str(href)):
                 print("=>",href)
                 if href not in urls:
                         urls.append(href)
                         #print("=>",num," ",href)
                         num += 1
                         #s = str(href)
                         #if "http://"  in href:
                         #    s = s.replace("http://","")
                         #    s = s.replace("/","_")
                         #    print(s)
                         #if "https://"  in href:
                         #    s = s.replace("http://","")
                         #    s = s.replace("/","_")
                         #urllib.request.urlretrieve(href,'D:/study/Advanced Computing concepts/url_pages/'+str(s)+'.html')#,'./'+link[link.find('/turnstile_')+1:] 
                         #print("length:",len(urls))
                         #f.write(href+"\n")
                         suburls(href)

def suburls(url):
     source_code = rq.get(url)
     #print(source_code)
     regex = re.compile(
         r'^(?:http|ftp)s?://' # http:// or https://
         r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)|' #domain...
         r'localhost|' #localhost...
         r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})' # ...or ip
         r'(?::\d+)?' # optional port
         r'(?:/?|[/?]\S+)$', re.IGNORECASE)
     plain_text = source_code.text
     soup = BeautifulSoup(plain_text)
     for link in soup.findAll('a'):
             href = link.get('href')
             #print("\t\t\t\t",href)
             if re.match(regex,str(href)):
                 print("=>",href)
                 if href not in urls:
                     if href not in sub_urls:
                         sub_urls.append(href)
                         #s = str(href)
                         #if "http://"  in href:
                         #    s = s.replace("http://","")
                         #    s = s.replace("/","_")
                         #    print(s)
                         #if "https://"  in href:
                         #    s = s.replace("http://","")
                         #    s = s.replace("/","_")
                         #urllib.request.urlretrieve(href,'D:/study/Advanced Computing concepts/url_pages/'+str(s)+'.html')#,'./'+link[link.find('/turnstile_')+1:] 
                         #print("length:",len(urls))
                         #f.write(href+"\n")

def create_html(urls):
    for href in urls:
            s = str(href)
            if "http://"  in href:
                s = s.replace("http://","")
                s = s.replace("/","_")
                print("=>",s)
            if "https://"  in href:
                s = s.replace("http://","")
                s = s.replace("/","_")
            if ".pdf" in href:
                continue
            #href = urllib.request.urlopen(href)
            urllib.request.urlretrieve(href,'D:/study/Advanced Computing concepts/url_pages/'+str(s)+'.html')#,'./'+link[link.find('/turnstile_')+1:] 
            #href.close()
            #print("length:",len(urls))
            f.write(href+"\n")   

def temp():
    
    for href in urls:
        try:
            s = str(href)
            print("=>",href)
            if "http://"  in href:
                s = s.replace("http://","")
                s = s.replace("/","_")
                print("=>",s)
            if "https://"  in href:
                s = s.replace("https://","")
                s = s.replace("/","_")
            if ".pdf" in href:
                continue
            file = open("D:/study/Advanced Computing concepts/url_pages/"+s+".html", "w")
            source_code = rq.get(href)
            #print(source_code)
            
            plain_text = source_code.text
            soup = BeautifulSoup(plain_text)
            #print(soup)
            #text = soup.find_all()
            #text = text
            file.write(str(soup.encode("utf-8")))
            file.close()
        except:
            continue

    
    
    
    
    
trade_spider(urls,1000, num)
print(len(urls))
print(len(sub_urls))
for link in sub_urls:
    if link not in urls:
        urls.append(link)
print(len(urls))
#create_html(urls)
temp()
#suburls(urls,sub_urls)
#print(urls)
f.close()
# for removing all repeating elements from list : my_list = list(set(my_list))