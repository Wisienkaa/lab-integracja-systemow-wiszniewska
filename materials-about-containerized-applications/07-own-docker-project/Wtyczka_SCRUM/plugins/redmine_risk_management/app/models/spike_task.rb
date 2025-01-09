class SpikeTask < ActiveRecord::Base
    belongs_to :risk
  
    validates :name, :status, presence: true
    STATUSES = %w[Pending InProgress Completed]
  end
  