package gr.ihu.auction.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author John
 */
@Entity
@Table(name = "auction", catalog = "auction_db", schema = "")
public class Auction implements Serializable {
    private static final long serialVersionUID = 4362314412438095175L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "id", nullable = false)
   private Integer id;

   @Column(name = "start_time", nullable = false)
   @Temporal(javax.persistence.TemporalType.TIMESTAMP)
   private Date startTime;
   
   @Column(name = "end_time", nullable = false)
   @Temporal(javax.persistence.TemporalType.TIMESTAMP)
   private Date endTime;

   @Column(name = "product_description", nullable = false, length = 255)
   private String productDescription;

   @Column(name = "owner_username", nullable = false)
   private String owner;

   @Column(name = "current_bid")
   private double currentBid;

   @Column(name = "minimum_bid", nullable = false)
   private double minimumBid;

   @Column(name = "auct_state", nullable = false)
   private String state;

   @Column(name = "init_price", nullable = false)
   private double initialPrice; //opening bid

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
   private List<AuctionBid> bids; 

   public static enum State {
      RUNNING, ENDED, PROCEEDED
   }
   
   public List<AuctionBid> getBids() {
      return bids;
   }

   public void setBids(List<AuctionBid> bids) {
      this.bids = bids;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getEndTime() {
      return endTime;
   }

   public void setEndTime(Date endTime) {
      this.endTime = endTime;
   }

   public Date getStartTime() {
      return startTime;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public String getProductDescription() {
      return productDescription;
   }

   public void setProductDescription(String productDescription) {
      this.productDescription = productDescription;
   }

   public String getOwner() {
      return owner;
   }

   public void setOwner(String owner) {
      this.owner = owner;
   }

   public double getCurrentBid() {
      return currentBid;
   }

   public void setCurrentBid(double currentBid) {
      this.currentBid = currentBid;
   }

   public double getMinimumBid() {
      return minimumBid;
   }

   public void setMinimumBid(double minimumBid) {
      this.minimumBid = minimumBid;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public double getInitialPrice() {
      return initialPrice;
   }

   public void setInitialPrice(double initialPrice) {
      this.initialPrice = initialPrice;
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
      final Auction other = (Auction) obj;
      if (!Objects.equals(this.id, other.id)) {
         return false;
      }
      return true;
   }

   
}
