firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-name WANv6_IN {
        default-action drop
        description "WAN inbound traffic forwarded to LAN"
        enable-default-log
        rule 10 {
            action accept
            description "Allow established/related sessions"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    ipv6-name WANv6_LOCAL {
        default-action drop
        description "WAN inbound traffic to the router"
        enable-default-log
        rule 10 {
            action accept
            description "Allow established/related sessions"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
        rule 30 {
            action accept
            description "Allow IPv6 icmp"
            protocol ipv6-icmp
        }
        rule 40 {
            action accept
            description "allow dhcpv6"
            destination {
                port 546
            }
            protocol udp
            source {
                port 547
            }
        }
    }
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name WAN_IN {
        default-action drop
        description "WAN to internal"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    name WAN_LOCAL {
        default-action drop
        description "WAN to router"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    options {
        mss-clamp {
            interface-type pppoe
            mss 1452
        }
    }
    receive-redirects disable
    send-redirects enable
    source-validation disable
    syn-cookies enable
}
interfaces {
    ethernet eth0 {
        description WAN
        duplex auto
        poe {
            output off
        }
        speed auto
        vif 630 {
            pppoe 0 {
                default-route auto
                mtu 1492
                name-server auto
                password AX319nOE4j
                user-id 14838054@astrobb.com.my
            }
        }
    }
    ethernet eth1 {
        address 192.168.10.1/24
        description Local
        duplex auto
        poe {
            output 24v
        }
        speed auto
    }
    ethernet eth2 {
        address 192.168.20.1/24
        description "Local 2"
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth3 {
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth4 {
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth5 {
        duplex auto
        speed auto
    }
    loopback lo {
    }
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN1 {
            authoritative enable
            subnet 192.168.10.0/24 {
                default-router 192.168.10.1
                dns-server 192.168.10.1
                domain-name local
                lease 86400
                start 192.168.10.2 {
                    stop 192.168.10.254
                }
                static-mapping EdgeRouter-X-5-Port {
                    ip-address 192.168.10.2
                    mac-address 74:83:c2:6d:dc:97
                }
                static-mapping NintendoSwitch {
                    ip-address 192.168.10.245
                    mac-address 94:58:cb:fa:97:0a
                }
                static-mapping NvidiaShield {
                    ip-address 192.168.10.128
                    mac-address 00:04:4b:89:14:02
                }
                static-mapping Soundbar {
                    ip-address 192.168.10.224
                    mac-address 70:26:05:31:aa:6d
                }
                static-mapping aimesh {
                    ip-address 192.168.10.10
                    mac-address 24:4b:fe:f3:44:e0
                }
                static-mapping aimeshnode {
                    ip-address 192.168.10.11
                    mac-address 24:4b:fe:f3:7a:30
                }
                static-mapping playstation {
                    ip-address 192.168.10.30
                    mac-address 78:c8:81:be:44:2d
                }
            }
        }
        shared-network-name LAN2 {
            authoritative enable
            subnet 192.168.20.0/24 {
                default-router 192.168.20.1
                dns-server 192.168.20.1
                lease 86400
                start 192.168.20.2 {
                    stop 192.168.20.254
                }
            }
        }
        static-arp disable
        use-dnsmasq enable
    }
    dns {
        forwarding {
            cache-size 10000
            listen-on eth1
            listen-on eth2
            name-server 1.1.1.1
            name-server 1.0.0.1
        }
    }
    gui {
        http-port 80
        https-port 443
        older-ciphers disable
    }
    nat {
        rule 5010 {
            description "masquerade for WAN"
            outbound-interface pppoe0
            type masquerade
        }
    }
    ssh {
        port 22
        protocol-version v2
    }
    unms {
        disable
    }
    upnp {
    }
}
system {
    analytics-handler {
        send-analytics-report false
    }
    crash-handler {
        send-crash-report false
    }
    host-name EdgeRouter-6P
    login {
        user dericleeyy {
            authentication {
                encrypted-password $5$06QG/Qn0pyO1xVnm$NwRDgzAYp72vfRsJSbGtR00FxOVNfwtUXiITtnzqoH.
            }
            full-name "Deric Lee"
            level admin
        }
    }
    ntp {
        server 0.ubnt.pool.ntp.org {
        }
        server 1.ubnt.pool.ntp.org {
        }
        server 2.ubnt.pool.ntp.org {
        }
        server 3.ubnt.pool.ntp.org {
        }
    }
    offload {
        hwnat disable
        ipsec enable
        ipv4 {
            bonding enable
            forwarding enable
            gre enable
            pppoe enable
            vlan enable
        }
        ipv6 {
            forwarding enable
            pppoe enable
        }
    }
    static-host-mapping {
        host-name aimesh.local {
            inet 192.168.10.10
        }
        host-name edgerouter6p.local {
            inet 192.168.10.1
        }
        host-name edgerouterx.local {
            inet 192.168.10.2
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone Asia/Kuala_Lumpur
    traffic-analysis {
        dpi disable
        export disable
    }
}
traffic-control {
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "config-management@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@5:nat@3:qos@1:quagga@2:suspend@1:system@5:ubnt-l2tp@1:ubnt-pptp@1:ubnt-udapi-server@1:ubnt-unms@2:ubnt-util@1:vrrp@1:vyatta-netflow@1:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: v2.0.9-hotfix.7.5622762.230615.1131 */
