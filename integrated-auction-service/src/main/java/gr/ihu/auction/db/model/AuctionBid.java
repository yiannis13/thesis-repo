package gr.ihu.auction.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author John
 */
@Entity
@Table(name = "auction_bid", catalog = "auction_db", schema = "")
public class AuctionBid implements Serializable {
    private static final long serialVersionUID = -727491108384919530L;
       
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "id", nullable = false)
   private Integer id;
   
   @Column(name = "bidder_username", nullable = false)
   private String bidder;
   
   @Column(name = "start_time", nullable = false)
   @Temporal(javax.persistence.TemporalType.TIMESTAMP)
   private Date when;
   
   @Column(name = "amount")
   private double amount;
   
   @JoinColumn(name = "auction_id", referencedColumnName = "id", nullable = false)
   @ManyToOne(optional = false)
   private Auction auction;

   
   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getBidder() {
      return bidder;
   }

   public void setBidder(String bidder) {
      this.bidder = bidder;
   }

   public Date getWhen() {
      return when;
   }

   public void setWhen(Date when) {
      this.when = when;
   }

   public double getAmount() {
      return amount;
   }

   public void setAmount(double amount) {
      this.amount = amount;
   }

   public Auction getAuction() {
      return auction;
   }

   public void setAuction(Auction auction) {
      this.auction = auction;
   }

   @Override
   public int hashCode() {
      int hash = 3;
      hash = 97 * hash + Objects.hashCode(this.id);
      return hash;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final AuctionBid other = (AuctionBid) obj;
      if (!Objects.equals(this.id, other.id)) {
         return false;
      }
      return true;
   }
}
