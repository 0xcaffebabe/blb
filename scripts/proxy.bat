:: 用来转发WSL端口与宿主机端口
netsh interface portproxy add v4tov4 listenport=6379 connectaddress=local connectport=6379 listenaddress=* protocol=tcp
netsh interface portproxy add v4tov4 listenport=9200 connectaddress=local connectport=9200 listenaddress=* protocol=tcp
netsh interface portproxy add v4tov4 listenport=9300 connectaddress=local connectport=9300 listenaddress=* protocol=tcp
netsh interface portproxy add v4tov4 listenport=5672 connectaddress=local connectport=5672 listenaddress=* protocol=tcp
netsh interface portproxy add v4tov4 listenport=15672 connectaddress=local connectport=15672 listenaddress=* protocol=tcp
netsh interface portproxy add v4tov4 listenport=8848 connectaddress=local connectport=8848 listenaddress=* protocol=tcp
netsh interface portproxy add v4tov4 listenport=3306connectaddress=local connectport=3306 listenaddress=* protocol=tcp